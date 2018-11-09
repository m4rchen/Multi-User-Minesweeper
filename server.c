#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <pthread.h>
#include <sys/select.h>
#include <errno.h>
#include "/usr/include/mysql/mysql.h"

#define BUF_SIZE 30
#define MAX_ROOM 100
#define MAX_USER 200

void userInfo(char *buf, char *stocMsg, int clntfd); 
void gameInfo(char *buf, char *stocMsg, int clntfd);
void refresh(char *stocMsg, int clntfd);

typedef struct player{
	int fd;
	char name[10];
	char idNum[3]; 
	int status; // 0 is wait, 1 is game
} PLAYER;

typedef struct roomInfo{
	char player1[3];
	char player2[3]; // player id num
	int posP1;
	int posP2;

	char roomName[10];

	int maxPlayer;
	int kindGame; // 0 > turn / 1 > raid
	int isFull;
} ROOMINFO;

ROOMINFO room[MAX_ROOM];
PLAYER players[MAX_USER];

MYSQL mysql;
MYSQL_RES *res;
MYSQL_ROW row;
int fields;
int playerCnt = 0, roomCnt = 0;

int main(int argc, char *argv[])
{
	int serv_sock, clnt_sock;
	socklen_t lenServ, lenClint;
	fd_set reads, cpy_reads;

	struct sockaddr_in serv_addr, clnt_addr;
	struct timeval timeout;
	fd_set testfds, readfds;

	socklen_t adr_sz;
	int fd_max, fd_num;
	int len, i;
	char buf[BUF_SIZE], stocMsg[BUF_SIZE], chatMsg[20];

	serv_sock = socket(AF_INET, SOCK_STREAM, 0);

	printf("Starting Minesweeper Server...");
	printf("OK\n");

	sleep(1);
	mysql_init(&mysql);
	mysql_real_connect(&mysql, NULL, "root", "5768", NULL, 3306, (char *) NULL, 0);
	mysql_query(&mysql, "use login_db;");

	printf("Connect to Mysql Datebase...");
	printf("OK\n\n");

	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(atoi(argv[1]));
	lenServ = sizeof(serv_addr);

	if(bind(serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) == -1){
		perror("Error : fail to bind\n");
		exit(1);
	}

	if(listen(serv_sock, 5) == -1){
		perror("Error : fail to listen\n");
		exit(1);
	}

	FD_ZERO(&reads);
	FD_SET(serv_sock, &reads);
	fd_max = serv_sock;

	while(1){
		cpy_reads = reads;
		timeout.tv_sec  = 5;
		timeout.tv_usec = 5000;

		if((fd_num = select(fd_max+1, &cpy_reads, 0, 0, &timeout)) == -1)
			break;
		if(fd_num == 0)
			continue;

		for(i = 0; i < fd_max + 1; i++){
			if(FD_ISSET(i, &cpy_reads))
			{
				if(i == serv_sock)
				{
					adr_sz = sizeof(clnt_addr);
					clnt_sock = accept(serv_sock, (struct sockaddr *) &clnt_addr, &adr_sz);
					FD_SET(clnt_sock, &reads);
					printf("Client Connected: #%d Socket\n", clnt_sock);

					if(fd_max < clnt_sock)
						fd_max = clnt_sock;
				}
				else{
					memset(buf, '\0', sizeof(buf));
					memset(stocMsg, '\0', sizeof(stocMsg));
					memset(chatMsg, '\0', sizeof(chatMsg));

					len = read(i, buf, sizeof(buf));

					if(len == -1){
						FD_CLR(i, &reads);
						close(i);
						printf("Client Disconnected: #%d Socket\n", i);
					}
					else{
						printf("#%d Socket Message >> %s\n", i, buf);
						if (buf[0] == '0') {
							printf("Refresh Method Execute\n");
							refresh(stocMsg, i);
							continue;
						}
						else if(buf[0] == '1'){ // user info msg
							printf("User Info Method Execute\n");

							userInfo(buf, stocMsg, i); 
							printf("Message to #%d Socket>> %s\n", i, stocMsg);
							//write(i, stocMsg, strlen(stocMsg)); // send msg to client i
						}
						else if(buf[0] == '2'){ // game info msg
							printf("Game Info Method Execute\n");

							gameInfo(buf, stocMsg, i);
							printf("Message to #%d Socket>> %s\n", i, stocMsg);
							//write(i, stocMsg, strlen(stocMsg)); // send msg to client i
							
						}
						else if(buf[0] == '3'){ // chat msg
							printf("Chat Message Method Execute\n");

							strtok(buf, "#"); // first
							int roomNumber = atoi(strtok(NULL, "#"));
							char tmpChat[20];
							
							strtok(NULL, "#\r\n");
							strcpy(tmpChat, strtok(NULL, "#\r\n"));
							printf("Message to #%d Room>> %s\n", roomNumber, tmpChat);
							strcat(tmpChat, "\r\n");
							
							write(players[room[roomNumber].posP1].fd, tmpChat, strlen(tmpChat));
							write(players[room[roomNumber].posP2].fd, tmpChat, strlen(tmpChat));

						}
					}
				}
			}
		}
	}

	close(serv_sock);
	return 0;
}

void userInfo(char *buf, char *stocMsg, int clntfd) {
	char query[100] = "";
	char temp[100] = "";
	char ret[100] = {'\0', };
	char ID[10] = {'\0', };
	char PASSWD[10] = {'\0', };
	char NUM[3] = {'\0', };
	char GAME;
	int set;
		
	strcpy(temp, buf); // copy
	strtok(temp, "#"); // first byte
	strtok(NULL, "#"); // second byte


	if(buf[2] == '1'){ // register
		strcpy(ID, strtok(NULL, "#"));// buf[2-11] >> ID
		strcpy(PASSWD, strtok(NULL, "#"));// buf[12 - 21] >> PW

		strcat(query, "select exists (select NUM from USER where ID = '");
		strcat(query, ID);
		strcat(query, "');");

		set = mysql_query(&mysql, query);

		if (!set) {
			res = mysql_store_result(&mysql);
			fields = mysql_num_fields(res);
			row = mysql_fetch_row(res);
		}

		if (row[0][0] == '0') {
			strcpy(stocMsg, "1\r\n");
			memset(query, '\0', sizeof(query));
			strcat(query, "insert into USER (ID, PASSWD) values('");
			strcat(query, ID);
			strcat(query, "', '");
			strcat(query, PASSWD);
			strcat(query, "');");
			mysql_query(&mysql, query);
			printf("Register Success>> ID: %s\n", ID);
		} else {
			//strcpy(stocMsg, "0\r\n");
			printf("Register Failure>> #%d Socket\n", clntfd);
		}
	} 
	else if(buf[2] == '2') { // login
		strcpy(ID, strtok(NULL, "#"));// buf[2-11] >> ID
		strcpy(PASSWD, strtok(NULL, "#"));// buf[12 - 21] >> PW

		strcat(query, "select exists (select NUM from USER where ID = '");
		strcat(query, ID);
		strcat(query, "' and PASSWD = '");
		strcat(query, PASSWD);
		strcat(query, "');");

		set = mysql_query(&mysql, query);

		if (!set) {
			res = mysql_store_result(&mysql);
			fields = mysql_num_fields(res);
			row = mysql_fetch_row(res);
		}

		if (row[0][0] == '1') {
			memset(query, '\0', sizeof(query));
			strcat(query, "select NUM from USER where ID = '");
			strcat(query, ID);
			strcat(query, "' and PASSWD = '");
			strcat(query, PASSWD);
			strcat(query, "';");
			mysql_query(&mysql, query);
			res = mysql_store_result(&mysql);
			fields = mysql_num_fields(res);
			row = mysql_fetch_row(res);
			strcpy(NUM, row[0]);
			strcpy(stocMsg, strcat(row[0], "\r\n"));
			write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
			printf("Login Success>> ID: %s\n", ID);

			players[playerCnt].fd = clntfd; // add connected player
			strcpy(players[playerCnt].name, ID);
			strcpy(players[playerCnt].idNum, NUM);
			players[playerCnt++].status = 0;
		} else {
			strcpy(stocMsg, ".\r\n"); // 접속 실패시 "." 보냄
			printf("Login Failure>> #%d Socket\n", clntfd);
		}
	}
	else if(buf[2] == '3') { // new score
		strcpy(NUM, strtok(NULL, "#"));// buf[2-4] >> ID num
		strcpy(&GAME, strtok(NULL, "#"));// buf[5] >> kind of game

		strcat(query, "select WIN, LOSE from USER where NUM = '");
		strcat(query, NUM);
		strcat(query, "';");

		mysql_query(&mysql, query);

		res = mysql_store_result(&mysql);
		fields = mysql_num_fields(res);
		row = mysql_fetch_row(res);

		mysql_query(&mysql, query); 

		strcat(ret, row[0]); // WIN
		strcat(ret, " / "); // /
		strcat(ret, row[1]); // LOSE
		strcat(ret, "\r\n");

		strcpy(stocMsg, ret);

		printf("User Record Read>> IDNUM: %s (%s / %s)\n", NUM, row[0], row[1]);
	}
	else if (buf[2] == '4') {
		strcpy(NUM, strtok(NULL, "#"));
		mysql_query(&mysql, "select ID, WIN, LOSE from USER order by WIN DESC;");
		res = mysql_store_result(&mysql);
		fields = mysql_num_fields(res);
		row = mysql_fetch_row(res);

		for (int j = 0; j < 5; j++) {
			row = mysql_fetch_row(res);
			for (int k = 0; k < fields; k++) {
				strcat(ret, row[k]);
				strcat(ret, "#");
			}
		}

		strcat(query, "select ID, WIN, LOSE from USER where NUM = '");
		strcat(query, NUM);
		strcat(query, "';");
		mysql_query(&mysql, query);

		res = mysql_store_result(&mysql);
		fields = mysql_num_fields(res);
		row = mysql_fetch_row(res);

		for (int j = 0; j < fields; j++) {
			strcat(ret, row[j]);
			strcat(ret, "#");
		}

		strcat(ret, "\r\n");
		strcpy(stocMsg, ret);
		printf("Top User Mode1 Record Read>> \n");
		write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
		
		memset(query, '\0', sizeof(query));
		memset(ret, '\0', sizeof(ret));
		memset(stocMsg, '\0', sizeof(stocMsg));
		
		mysql_query(&mysql, "select ID, WIN2, LOSE2 from USER order by WIN2 DESC;");
		res = mysql_store_result(&mysql);
		fields = mysql_num_fields(res);
		row = mysql_fetch_row(res);

		for (int j = 0; j < 5; j++) {
			row = mysql_fetch_row(res);
			for (int k = 0; k < fields; k++) {
				strcat(ret, row[k]);
				strcat(ret, "#");
			}
		}

		strcat(query, "select ID, WIN2, LOSE2 from USER where NUM = '");
		strcat(query, NUM);
		strcat(query, "';");
		mysql_query(&mysql, query);

		res = mysql_store_result(&mysql);
		fields = mysql_num_fields(res);
		row = mysql_fetch_row(res);

		for (int j = 0; j < fields; j++) {
			strcat(ret, row[j]);
			strcat(ret, "#");
		}

		strcat(ret, "\r\n");
		strcpy(stocMsg, ret);
		printf("Top User Mode2 Record Read>> \n");
		write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
	}
}

void gameInfo(char *buf, char *stocMsg, int clntfd) {
	char query[1024] = {'\0', };
	char ret[100] = {'\0', };
	char ret1[100] = {'\0', };
	char temp[30] = {'\0', };
	char NUM[3] = {'\0' };
	char ROOM[3] = {'\0', };
	char RESULT = '\0';
	int roomNumber;

	strcpy(temp, buf);
	strtok(temp, "#"); // frist
	strtok(NULL, "#"); // second

	if(buf[2] == '1') { // mine
		strcat(ret, buf);
		strcat(ret, "\r\n");
		strcpy(stocMsg, ret);
		write(players[room[roomNumber].posP1].fd, stocMsg, strlen(stocMsg));
		write(players[room[roomNumber].posP2].fd, stocMsg, strlen(stocMsg));
	} 
	else if(buf[2] == '2'){ // new room
		if(roomCnt == MAX_ROOM){ // can not make new room
			for(int i = 0; i < MAX_ROOM; i++){
				if(strcmp(room[i].player1, "\0") == 0){ // find empty room
					roomCnt = i;
					break;
				}
			}
		}

		room[roomCnt].kindGame = atoi(strdup(strtok(NULL, "#"))); // add new room info
		room[roomCnt].maxPlayer = atoi(strdup(strtok(NULL, "#")));
		strcpy(room[roomCnt].roomName, strtok(NULL, "#"));
		strcpy(room[roomCnt].player1, strtok(NULL, "#"));
		room[roomCnt].isFull = 1; // wait

		for(int i = 0; i < playerCnt; i++){
			if(strncmp(room[roomCnt].player1, players[i].idNum, strlen(players[i].idNum)) == 0){
				room[roomCnt].posP1 = i;
				players[i].status = 1;
				break;
			}
		} // find player1 >> change status 0 > 1
		
		sprintf(ret, "%d\r\n", roomCnt);// send roomCnt to client
		strcpy(stocMsg, ret);
		printf("Make Room>> RoomNum: %d\n", roomCnt);
		write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
		roomCnt++;
	}
	else if(buf[2] == '3'){ // enter room
		roomNumber = atoi(strdup(strtok(NULL, "#")));

		if(room[roomNumber].isFull == room[roomNumber].maxPlayer){
			// room is full
		}
		else{
			strcpy(room[roomNumber].player2, strtok(NULL, "#")); // add new player info
			room[roomNumber].isFull++;

			for(int i = 0; i < playerCnt; i++){
				if(strcmp(room[roomNumber].player2, players[i].idNum) == 0){
					room[roomNumber].posP2 = i;
					players[i].status = 1;
					break;
				}
			} // find player2 >> change status 0 > 1
		}

		strcpy(ret, players[room[roomNumber].posP2].name);
		strcpy(ret1, players[room[roomNumber].posP1].name);
		strcat(ret, "\r\n");
		strcat(ret1, "\r\n");

		strcpy(stocMsg, ret);
		write(players[room[roomNumber].posP1].fd, stocMsg, strlen(stocMsg)); // send msg to client captain
		memset(stocMsg, '\0', sizeof(stocMsg));
		strcpy(stocMsg, ret1);
		write(players[room[roomNumber].posP2].fd, stocMsg, strlen(stocMsg)); // send msg to client customer
	}
	else if(buf[2] == '4'){ // exit room
		roomNumber = atoi(strdup(strtok(NULL, "#")));

		char exitPlayer[3];
		strcpy(exitPlayer, strtok(NULL, "#"));

		if(strcmp(exitPlayer, room[roomNumber].player1) == 0){ // if player1 >> room delete
			players[room[roomNumber].posP1].status = 0;
			players[room[roomNumber].posP2].status = 0; // change status p1, p2 1 > 0

			memset(&room[roomNumber], '\0', sizeof(ROOMINFO));
		} 
		else{ // if player2 >> room not delete
			players[room[roomNumber].posP2].status = 0; // change status p1, p2 1 > 0
			room[roomNumber].isFull = 0;
			room[roomNumber].posP2 = 0;
		}
	}
	else if(buf[2] == '5'){ // start game
		roomNumber = atoi(strdup(strtok(NULL, "#")));

	}
	else if(buf[2] == '6') { // result game
		
		printf("Result Update>>\n");

		strcpy(ROOM, strtok(NULL, "#"));
		strcpy(NUM, strtok(NULL, "#"));
		strcpy(&RESULT, strtok(NULL, "#"));

		if (RESULT == '0') { // lose
			strcat(query, "update USER set LOSE = LOSE + 1 where NUM = '");
			strcat(query, NUM);
			strcat(query, "';");
			mysql_query(&mysql, query);
		} else if (RESULT == '1') { // win
			strcat(query, "update USER set WIN = WIN + 1 where NUM = '");
			strcat(query, NUM);
			strcat(query, "';"); 
			mysql_query(&mysql, query);
		} 
		
	} 
}

void refresh(char *stocMsg, int clntfd){
	char msgRoomName[40] = "\0";
	char player_name[10] = "\0";
	char kind[2][5] = {"TURN", "RAID"};

	printf("Message to #%d Socket>>\n", clntfd);

	for(int i = 0; i < 8; i++){
		memset(stocMsg, '\0', sizeof(stocMsg));

		if(i >= roomCnt){
			strcpy(stocMsg, "No Room\r\n");
			printf("%d] : No Room\n", i);
			write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
			continue;
		}

		strcpy(player_name, players[room[i].posP1].name);
		sprintf(msgRoomName, "%s] %s(%d/%d) _ %s\r\n", kind[room[i].kindGame], 
				room[i].roomName, room[i].isFull, room[i].maxPlayer, player_name);

		strcpy(stocMsg, msgRoomName);
		printf("%d] : %s\n", i, stocMsg);
		write(clntfd, stocMsg, strlen(stocMsg)); // send msg to client i
	}
}

