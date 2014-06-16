-- src/main/scripts/ttt-create.sql

-- Create all the tables and other schema elements (e.g. constraints, indexes, and so on).
-- Add a user cysun with password abcd and email cysun@localhost.localdomain.
-- Add two completed games by cysun; both games were against the AI player with one win and one loss.
-- Add a saved game by cysun. In the game cysun occupied the upper-left cell and the AI player occupied the center cell.

    create table game_boards (
        game_id int4 not null,
        boardCell int4,
        board_order int4 not null,
        primary key (game_id, board_order)
    );

    create table games (
        id int4 not null,
        player1_id int4,
        player2_id int4,
        startDate timestamp,
        endDate timestamp,
        saveDate timestamp,
        outcome varchar(255), 
        turn boolean not null default 't',
        primary key (id)
    );

    create table users (
        id int4 not null,
        username varchar(255) unique not null,
        password varchar(255) not null,
        email varchar(255) not null,
        enabled boolean not null default 't',
        primary key (id)
    );

     create table authorities (
        username varchar(255) not null,
        authority varchar(255)
    );
    
    alter table users 
        add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table game_boards 
        add constraint FK_nlm3nx402p9l6obyvjuydnn02 
        foreign key (game_id) 
        references games;

    alter table games 
        add constraint FK_gf5dddpb3pb14c1057sbdgn43 
        foreign key (player1_id) 
        references users;

    alter table games 
        add constraint FK_bv9atljedlpewpf76urp88mqv 
        foreign key (player2_id) 
        references users;
        
    create sequence hibernate_sequence minvalue 100;

--  Add a user cysun with password abcd and email cysun@localhost.localdomain.
    insert into users values (1,'cysun', 'abcd','cysun@localhost.localdomain', true);
    insert into users values (2,'AI', 'AI', 'AI',true);   
    select * from users;
    
    insert into authorities values('AI', 'ROLE_ADMIN');
    insert into authorities values('cysun', 'ROLE_USER');

     
-- Add two games played by cysun against the AI player; 
-- one of them is a win and the other one a loss.
   insert into games values (1, 1, 2, current_timestamp, current_timestamp, null, 'win', false);
   insert into games values (2, 1, 2, current_timestamp, current_timestamp, null, 'loss', false);
   select * from games;
   
-- Add a saved game by cysun. 
-- In the game cysun occupied the upper-left cell and the AI player occupied the center cell.
   insert into games values (3, 1, 2, current_timestamp, null, current_timestamp, null, true);
   
   insert into game_boards values (3, 1, 0);
   insert into game_boards values (3, 0, 1);
   insert into game_boards values (3, 0, 2);
   insert into game_boards values (3, 0, 3);
   insert into game_boards values (3, 2, 4);
   insert into game_boards values (3, 0, 5);
   insert into game_boards values (3, 0, 6);
   insert into game_boards values (3, 0, 7);
   insert into game_boards values (3, 0, 8);
   
   select * from games;
   select * from game_boards;