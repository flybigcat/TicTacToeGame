
    create table authorities (
        username varchar(255) not null,
        authority varchar(255)
    );

    create table game_boards (
        game_id int4 not null,
        boardCell int4,
        board_order int4 not null,
        primary key (game_id, board_order)
    );

    create table games (
        id int4 not null,
        endDate timestamp,
        outcome varchar(255),
        saveDate timestamp,
        startDate timestamp,
        turn boolean not null,
        boardString varchar(255),
        player1_id int4,
        player2_id int4,
        primary key (id)
    );

    create table users (
        id int4 not null,
        email varchar(255) not null,
        enabled boolean not null,
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
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

    alter table games 
        add constraint FK_gf5dddpb3pb14c1057sbdgn43 
        foreign key (player1_id) 
        references users;

    alter table games 
        add constraint FK_bv9atljedlpewpf76urp88mqv 
        foreign key (player2_id) 
        references users;

    create sequence hibernate_sequence;
