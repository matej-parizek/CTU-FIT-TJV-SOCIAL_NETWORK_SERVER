INSERT INTO user_account (username, real_name, info) VALUES
                                                         ('user1', 'John Doe', 'Info 1'),
                                                         ('user2', 'Jane Smith', 'Info 2'),
                                                         ('user3', 'Alice Johnson', null),
                                                         ('user4', 'Bob Anderson', 'Info 4'),
                                                         ('user5', 'Eva Wilson', null);
insert into user_authorization(username, password) values
                                                       ('user1', 'password'),
                                                       ('user2','password'),
                                                       ('user3', 'password'),
                                                       ('user4','password'),
                                                       ('user5', 'password');

INSERT INTO user_account_followed (followed_username, followers_username) VALUES
                                                                              ('user1', 'user2'),
                                                                              ('user1', 'user3'),
                                                                              ('user2', 'user1'),
                                                                              ('user3', 'user1'),
                                                                              ('user3', 'user4');

INSERT INTO post (added, text, author_username, id, image) VALUES
                                                               ('2024-01-06T12:00:00', 'Text of the post 1', 'user1', 1, lo_import('/docker-entrypoint-initdb.d/img1')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 2', 'user1', 0, lo_import('/docker-entrypoint-initdb.d/img1')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 3', 'user2', 0, lo_import('/docker-entrypoint-initdb.d/img2')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 4', 'user3', 0, lo_import('/docker-entrypoint-initdb.d/img3')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 5', 'user4', 0, lo_import('/docker-entrypoint-initdb.d/img4')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 6', 'user4', 1, lo_import('/docker-entrypoint-initdb.d/img3')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 7', 'user5', 0, lo_import('/docker-entrypoint-initdb.d/img5')),
                                                               ('2024-01-06T12:00:00', 'Text of the post 8', 'user5', 1, lo_import('/docker-entrypoint-initdb.d/img1'));

INSERT INTO post_likes (post_id, likes_username, post_author_username) VALUES
                                                                           (0, 'user2', 'user1'),  -- user2 likes user1's post with post_id 1
                                                                           (1, 'user2', 'user1'),  -- user2 likes user1's post with post_id 1
                                                                           (0, 'user3', 'user1'),  -- user3 likes user1's post with post_id 2
                                                                           (1, 'user3', 'user1'),  -- user3 likes user1's post with post_id 2
                                                                           (0, 'user1', 'user2'),
                                                                           (0, 'user4', 'user3');  -- user4 likes user3's post with post_id 4
INSERT INTO comment (id_comment, to_post_id, author_username, text, to_post_author_username) VALUES
                                                                                                 (1, 1, 'user2', 'Comment on post 1', 'user1'),       -- user2 comments on user1's post 1
                                                                                                 (2, 1, 'user3', 'Comment on post 1', 'user1'),       -- user3 comments on user1's post 1
                                                                                                 (3, 0, 'user1', 'Comment on post 1', 'user2'),       -- user1 comments on user2's post 1
                                                                                                 (4, 0, 'user4', 'Comment on post 1', 'user3'),       -- user4 comments on user3's post 1
                                                                                                 (5, 0, 'user5', 'Comment on post 2', 'user1'),       -- user5 comments on user1's post 2
                                                                                                 (6, 0, 'user1', 'Comment on post 2', 'user1'),       -- user1 comments on own post 2
                                                                                                 (7, 0, 'user2', 'Comment on post 2', 'user5'),       -- user2 comments on user5's post 2
                                                                                                 (8, 0, 'user3', 'Comment on post 2', 'user5');       -- user3 comments on user5's post 2
