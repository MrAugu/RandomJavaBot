-- Users Table
CREATE TABLE users (
    user_id varchar(21),
    guild_id varchar(21),
    first_seen timestamp,
    balance decimal(11, 3),
    experience int,
    experience_level int,
    last_acknowledged_level int,
    PRIMARY KEY (user_id)
);

-- Guilds Table
CREATE TABLE guilds (
    guild_id varchar(21),
    multiplier_xp decimal(3, 3),
    multiplier_balance decimal(3, 3),
    currency blob,
    PRIMARY KEY (guild_id)
 );