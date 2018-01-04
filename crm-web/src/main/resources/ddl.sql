DROP DATABASE crm;
CREATE DATABASE crm DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

select user0_.user_id as user_id1_0_, user0_.create_date as create_d2_0_, user0_.nick_name as nick_nam3_0_, user0_.password as password4_0_, user0_.salt as salt5_0_, user0_.status as status6_0_, user0_.update_date as update_d7_0_, user0_.user_name as user_nam8_0_ from sys_user user0_ where user0_.user_name like ? limit ?, ?


