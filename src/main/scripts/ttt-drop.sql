   alter table if exists game_boards 
        drop constraint if exists FK_nlm3nx402p9l6obyvjuydnn02;

    alter table if exists games 
        drop constraint if exists FK_gf5dddpb3pb14c1057sbdgn43;

    alter table if exists games 
        drop constraint if exists FK_bv9atljedlpewpf76urp88mqv;
        
    alter table if exists users 
        drop constraint if exists UK_r43af9ap4edm43mmtq01oddj6;

    drop table if exists users cascade;
    
     drop table if exists authorities;
 
    drop table if exists game_boards;

    drop table if exists games;

    drop sequence if exists hibernate_sequence;
