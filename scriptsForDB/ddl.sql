use final;

CREATE TABLE userProfile
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fullName VARCHAR(50) NOT NULL,
    role enum('ROLE_USER', 'ROLE_ADMIN') NOT NULL,
    avgRating DOUBLE,
    userLoginId BIGINT NOT NULL UNIQUE
);
DESCRIBE userProfile;
COMMIT;

CREATE TABLE userLogin
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL
);
DESCRIBE userLogin;
COMMIT;

ALTER TABLE userProfile ADD FOREIGN KEY (userLoginId)
    REFERENCES userLogin (id);

CREATE TABLE category
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    categoryName VARCHAR(50) NOT NULL
);
DESCRIBE category;
COMMIT;


CREATE TABLE chat
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);
DESCRIBE chat;
COMMIT;

CREATE TABLE usersChats
(
    chatId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    CONSTRAINT fk_chatsUserId
        FOREIGN KEY (chatId)  REFERENCES chat (id),
    CONSTRAINT fk_chatsChatId
        FOREIGN KEY (userid)  REFERENCES userProfile (id)
);
DESCRIBE usersChats;
COMMIT;

CREATE TABLE message
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    chatId BIGINT NOT NULL,
    userSenderId BIGINT NOT NULL,
    text VARCHAR(200) NOT NULL,
    creationDate DATE NOT NULL,
    CONSTRAINT fk_messageChatId
        FOREIGN KEY (chatId)  REFERENCES chat (id),
    CONSTRAINT fk_userSenderId
        FOREIGN KEY (userSenderId)  REFERENCES userProfile (id)
);
DESCRIBE message;
COMMIT;

CREATE TABLE ad
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    adName VARCHAR(50) NOT NULL,
    adStatus enum('OPEN', 'CLOSED') NOT NULL,
    categoryId BIGINT NOT NULL,
    adDescription VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL,
    premiumUntilDate DATE,
    userId BIGINT NOT NULL,
    creationDate DATE NOT NULL,
    CONSTRAINT fk_categoryId
        FOREIGN KEY (categoryId)  REFERENCES category (id),
    CONSTRAINT fk_adsUserId
        FOREIGN KEY (userId)  REFERENCES userProfile (id)
);
DESCRIBE ad;
COMMIT;

CREATE TABLE maintenance
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    maintenanceName VARCHAR(20)  NOT NULL,
    maintenanceDescription VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL,
    plusDays INT NOT NULL
);
DESCRIBE maintenance;
COMMIT;

CREATE TABLE adsMaintenances
(
    adId BIGINT NOT NULL,
    maintenanceId BIGINT NOT NULL,
    CONSTRAINT fk_adsMaintenances
        FOREIGN KEY (adId)  REFERENCES ad (id),
    CONSTRAINT fk_maintenancesAds
        FOREIGN KEY (maintenanceId)  REFERENCES maintenance (id)
);
DESCRIBE adsMaintenances;
COMMIT;

CREATE TABLE comment
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    adId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    commentText VARCHAR(200) NOT NULL,
    creationDate DATE NOT NULL,
    CONSTRAINT fk_commentsAd
        FOREIGN KEY (adId)  REFERENCES ad (id),
    CONSTRAINT fk_commentsUser
        FOREIGN KEY (userId)  REFERENCES userProfile (id)
);
DESCRIBE comment;
COMMIT;

CREATE TABLE rating
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    rating INT NOT NULL,
    userSenderId BIGINT NOT NULL,
    userReceiverId BIGINT NOT NULL,
    creationDate DATE NOT NULL,
    CONSTRAINT fk_userSenderIdRatings
        FOREIGN KEY (userSenderId)  REFERENCES userProfile (id),
    CONSTRAINT fk_userReceiverIdRatings
        FOREIGN KEY (userReceiverId)  REFERENCES userProfile (id)
);
DESCRIBE rating;
COMMIT;
