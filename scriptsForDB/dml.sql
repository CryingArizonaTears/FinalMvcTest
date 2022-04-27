INSERT INTO userLogin (id, username, password) VALUES
(1, 'test1Admin', '$2a$10$trZRtoGkWSXtoNaqTIYsT.JO2qRWr54cusT2JfOB9tJ4NZt7p1QCe'),
(2, 'test2User', '$2a$10$e1QSAtBHHXslNEDgaPz/zukqhxfw/dMqmAtuFs9CiCE2e1oooWG/G'),
(3, 'test3User', '$2a$10$5UYioHaJ8kQmpDiwSmDz9uDlWbLJwvELXvavbM7cjCk7s.x7sYJLG');

INSERT INTO userProfile (id, fullName, role, avgRating, userLoginId) VALUES
(1, 'testName1Admin', 'ROLE_ADMIN', 0, 1),
(2, 'testName2User', 'ROLE_USER', 0, 2),
(3, 'testName3User', 'ROLE_USER', 0, 3);

INSERT INTO category (id, categoryName) VALUES
(1, 'testCategory1'),
(2, 'testCategory2'),
(3, 'testCategory3');

INSERT INTO ad (id, adName, adStatus, categoryId, adDescription, price, premiumUntilDate, userId, creationDate) VALUES
(1, 'test1Ad', 'OPEN', 1, 'test1Description', 1, null, 1, '2001-01-01'),
(2, 'test2Ad', 'OPEN', 2, 'test2Description', 2, null, 2, '2002-01-01'),
(3, 'test3Ad', 'CLOSED', 3, 'test3Description', 3, null, 3, '2003-01-01');