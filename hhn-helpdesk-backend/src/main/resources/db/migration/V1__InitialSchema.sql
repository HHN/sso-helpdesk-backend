create sequence hibernate_sequence start with 1 increment by 1;

create table account_credential (
                                   id bigint not null,
                                   created timestamp not null,
                                   iv bytea not null,
                                   password varchar(255) not null,
                                   salt varchar(255) not null,
                                   seq varchar(255) not null,
                                   used timestamp,
                                   location_id bigint not null,
                                   primary key (id)
);

create table audit_log_entry (
                               id bigint not null,
                               action varchar(255) not null,
                               actor varchar(1024) not null,
                               params varchar(10000) not null,
                               time timestamp not null,
                               primary key (id)
);

create table Location (
                          id bigint not null,
                          label varchar(255) not null,
                          primary key (id)
);

alter table account_credential
    add constraint UK_q4x3hi7dpppcc1qyuot86dmtg unique (seq);

alter table location
    add constraint UK_6hrs7pb7un9i24b3fa1a9aegs unique (label);

alter table account_credential
    add constraint FKlquvf519kymgi1m06asj2nb2x
        foreign key (location_id)
            references Location;
