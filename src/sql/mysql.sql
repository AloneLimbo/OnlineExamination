CREATE TABLE user
(
  user_id VARCHAR(36) PRIMARY KEY NOT NULL,
  user_name VARCHAR(30) NOT NULL UNIQUE ,
  real_name VARCHAR(15) NOT NULL,
  password VARCHAR(40) NOT NULL,
  user_status TINYINT(1) NOT NULL,
  user_state BIT(1) DEFAULT 1 NOT NULL,
  branch_id VARCHAR(36) NOT NULL,
  user_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  email VARCHAR(60) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

CREATE TABLE branch
(
  branch_id VARCHAR(36) PRIMARY KEY NOT NULL,
  branch_name VARCHAR(40) NOT NULL,
  branch_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  founder_id VARCHAR(36) NOT NULL,
  branch_state TINYINT(1) DEFAULT '1'
)ENGINE = InnoDB DEFAULT CHARSET =utf8;
CREATE TABLE exam
(
  exam_id VARCHAR(36) PRIMARY KEY NOT NULL,
  exam_name VARCHAR(30) NOT NULL,
  exam_start_date DATETIME NOT NULL,
  exam_end_date DATETIME NOT NULL,
  exam_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  paper_id VARCHAR(36),
  exam_state TINYINT(1) DEFAULT '1',
  founder_id VARCHAR(36),
  exam_time INT(11) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET =utf8;
CREATE TABLE exam_branch
(
  id VARCHAR(36) PRIMARY KEY NOT NULL,
  branch_id VARCHAR(36) NOT NULL ,
  exam_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (branch_id) REFERENCES branch(branch_id),
  FOREIGN KEY (exam_id) REFERENCES exam(exam_id)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;
CREATE TABLE function
(
  function_id VARCHAR(36) PRIMARY KEY NOT NULL,
  function_name VARCHAR(15) NOT NULL,
  function_icon VARCHAR(60) NOT NULL ,
  function_url VARCHAR(120) NOT NULL ,
  is_parent TINYINT(1)  DEFAULT 0,
  pid VARCHAR(36) DEFAULT '#',
  status TINYINT(4) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET =utf8;
CREATE TABLE paper
(
  paper_id VARCHAR(36) PRIMARY KEY NOT NULL,
  paper_name VARCHAR(40) NOT NULL,
  paper_state TINYINT(1) DEFAULT '1',
  paper_question TEXT NOT NULL,
  founder_id VARCHAR(36) NOT NULL,
  paper_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  paper_score INT(11) NOT NULL,
  questionNum INT(11) NOT NULL,
  FOREIGN KEY (founder_id) REFERENCES user(user_id)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

CREATE TABLE `question` (
  `question_id` varchar(36) NOT NULL,
  `question_title` text NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `question_create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_what` tinyint(4) NOT NULL,
  `question_state` tinyint(1) DEFAULT '1',
  `question_bank_id` varchar(36) NOT NULL,
  `founder_id` varchar(36) NOT NULL,
  `question_level` tinyint(4) DEFAULT '0',
  `question_analyze` text,
  PRIMARY KEY (`question_id`),
  FOREIGN KEY (founder_id) REFERENCES user(user_id),
  FOREIGN KEY (question_bank_id) REFERENCES question_bank(question_bank_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE question_bank
(
  question_bank_id VARCHAR(36) PRIMARY KEY NOT NULL,
  question_bank_name VARCHAR(30),
  question_bank_create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  founder_id VARCHAR(36) NOT NULL,
  question_bank_state TINYINT(1) DEFAULT '1',
  question_num INT(11) DEFAULT '0',
  single_answer_num INT(11) DEFAULT '0',
  tf_num INT(11) DEFAULT '0',
  answers_num INT(11) DEFAULT '0',
  FOREIGN KEY (founder_id) REFERENCES user(user_id)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

CREATE TABLE flaw_sweeper(
  id VARCHAR(36),
  founder_id VARCHAR(36) NOT NULL,
  question_id VARCHAR(36) NOT NULL ,
  examinee_answer VARCHAR(200) NOT NULL,
  exam_id VARCHAR(36) NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (exam_id) REFERENCES exam(exam_id),
  FOREIGN KEY (founder_id) REFERENCES user(user_id),
  FOREIGN KEY (question_id) REFERENCES question(question_id)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

CREATE TABLE report_card(
  id VARCHAR(36) ,
  score int DEFAULT 0,
  founder_id VARCHAR(36) NOT NULL ,
  create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  exam_id VARCHAR(36) NOT NULL ,
  state BIT(1) DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (founder_id) REFERENCES user(user_id),
  FOREIGN KEY (exam_id) REFERENCES exam(exam_id)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

CREATE TABLE history_exam(
  id VARCHAR(36),
  exam_score int NOT NULL ,
  exam_id VARCHAR(36) NOT NULL ,
  state BIT(1) NOT NULL ,
  founder_id VARCHAR(36) NOT NULL ,
  create_date DATETIME DEFAULT now(),
  autoScoring_questions TEXT NOT NULL ,
  subjective_questions TEXT NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (exam_id) REFERENCES exam(exam_id),
  FOREIGN KEY (founder_id) REFERENCES user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#删除用户触发器
CREATE TRIGGER user_delete_TRIGGER BEFORE DELETE
  ON user FOR EACH ROW
  BEGIN
    IF (old.user_status=2)
      THEN
      DELETE FROM branch WHERE founder_id=old.user_id;
      DELETE FROM exam WHERE founder_id=old.user_id;
      DELETE FROM question_bank WHERE founder_id=old.user_id;
      DELETE FROM paper WHERE founder_id=old.user_id;
    END IF;
    IF (old.user_status=3)
      THEN
        DELETE FROM flaw_sweeper WHERE founder_id = old.user_id;
        DELETE FROM report_card WHERE founder_id = old.user_id;
        DELETE FROM history_exam WHERE founder_id = old.user_id;
      END IF;
  END;


#考务管理员注册触发器
CREATE TRIGGER user_insert_trigger BEFORE INSERT
  ON user FOR EACH ROW
  BEGIN
    IF (new.user_status=2)
    THEN
      INSERT INTO branch(branch_id, branch_name, branch_create_date, founder_id, branch_state)
        VALUE(new.branch_id,'总部',now(),new.user_id,1);
    END IF;
  END;


#考试表删除触发器
CREATE TRIGGER exam_delete_trigger BEFORE DELETE
  ON exam FOR EACH ROW
  BEGIN
    DELETE FROM exam_branch WHERE exam_branch.exam_id = old.exam_id;
    DELETE FROM history_exam WHERE history_exam.exam_id = old.exam_id;
    DELETE FROM flaw_sweeper WHERE flaw_sweeper.exam_id = old.exam_id;
    DELETE FROM report_card WHERE report_card.exam_id = old.exam_id;
  END;


#部门删除触发器
CREATE TRIGGER branch_delete_trigger BEFORE DELETE
  ON branch FOR EACH ROW
  BEGIN
    DELETE FROM exam_branch WHERE exam_branch.branch_id=old.branch_id;
    DELETE FROM user WHERE user.branch_id=old.branch_id AND user.user_status=3;
  END;

#试题删除触发器
CREATE TRIGGER question_delete_trigger BEFORE DELETE
  ON question FOR EACH ROW
  BEGIN
    DELETE FROM flaw_sweeper WHERE flaw_sweeper.question_id = old.question_id;
  END;

#题库删除触发器
CREATE TRIGGER question_bank_delete_trigger BEFORE DELETE
  ON question_bank FOR EACH ROW
  BEGIN
    DELETE FROM question WHERE question.question_bank_id = old.question_bank_id;
  END;


#function data
INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
VALUES(uuid(),'个人中心','','#',0,NULL ,2),
  (uuid(),'首页','','admin.home',0,NULL ,2),
  (uuid(),'考生管理','','#',1,NULL ,2),
  (uuid(),'考试管理','','#',1,NULL ,2),
  (uuid(),'考试分析','','#',1,NULL ,2),
  (uuid(),'部门管理','','admin.branchEdit',0,NULL ,2);

#用户管理
INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'考生信息','','admin.userInfo',0,function_id,2 FROM function WHERE function_name='考生管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'添加考生','','admin.addUser',0,function_id,2 FROM function WHERE function_name='考生管理';

#考试管理
INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'创建考试','','admin.createExam',0,function_id,2 FROM function WHERE function_name='考试管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'试题库','','admin.questionBank',0,function_id,2 FROM function WHERE function_name='考试管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'添加试题','','admin.addQuestions',0,function_id,2 FROM function WHERE function_name='考试管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'试卷库','','admin.paperBank',0,function_id,2 FROM function WHERE function_name='考试管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'考试信息管理','','#',0,function_id,2 FROM function WHERE function_name='考试管理';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'成绩查阅批改','','#',0,function_id,2 FROM function WHERE function_name='考试管理';

#考试分析

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'分析考试成绩','','#',0,function_id,2 FROM function WHERE function_name='考试分析';

INSERT INTO function(function_id, function_name, function_icon, function_url, is_parent, pid, status)
  SELECT uuid(),'分析人员成绩','','#',0,function_id,2 FROM function WHERE function_name='考试分析';
