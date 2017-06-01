(function(angular) {
    angular.module('OnlineExamApp')
        .controller('LoginController',['$rootScope','$scope',
            'UserService','$http',
            function ($rootScope,$scope,UserService,$http) {
                $scope.login_data={};
                $scope.passwd={password:"",passwordAgain:""};

                $scope.signUp_data={};
                $scope.signUp_user_name_exists=false;
                $scope.isShow=true;

                $scope.pageSwitch=function () {
                    $scope.isShow=!$scope.isShow;
                    if(!$scope.isShow){
                        $scope.passwd={password:"",passwordAgain:""};
                        $scope.signUp_data={};
                    }
                };
                $scope.login = function () {
                    var $modal = $('#my-modal-loading');
                    $modal.modal();
                    UserService.login($scope.login_data).then(function (result) {
                        if (result.code == 0) {
                            $rootScope.$state.token = result.data;
                            $http.defaults.headers.common.Authorization = 'Bearer ' + $rootScope.$state.token;
                            $rootScope.$state.isLogin=true;
                            UserService.getUserName()
                                .then(function (result) {
                                    if(result.code==0){
                                       $rootScope.userName = result.data;
                                    }
                                });
                            $modal.modal('close');
                            $rootScope.$state.go('admin');
                        }
                        else {
                            $modal.modal('close');
                            alert("登录失败:"+result.msg);
                        }
                    },function (e) {
                        console.log(e);
                    })
                };


                $scope.signUp = function () {
                    $scope.signUp_data.password=hex_sha1($scope.passwd.password);
                    UserService.signUp($scope.signUp_data).then(function (result) {
                        if(result.code==0){
                            alert("注册成功！点击跳转登录...");
                            $scope.pageSwitch();
                        }else {
                            alert("注册失败："+result.msg);
                        }
                    },function (e) {
                        console.log(e);
                    })
                };

                $scope.userNameExists=function () {
                    UserService.userNameExists($scope.signUp_data.user_name)
                        .then(function (result) {
                            $scope.signUp_user_name_exists = result.code != 0;
                        },function (e) {
                            console.log(e);
                        })
                };
                $scope.$watch(function () {
                    return $scope.signUp_data;
                }, function (n,o) {
                    if(n.user_name!=o.user_name&&n.user_name!=''){
                        $scope.userNameExists();
                    }
                }, true);
            }])
        .controller('SignUpController',['$rootScope',
            '$scope',
            'UserService',function ($rootScope,$scope,UserService) {
                $scope.password="";
                $scope.passwordAgain="";
                $scope.signUp_data={};
                $scope.signUp_user_name_exists=false;
                $scope.signUp = function () {
                    $scope.signUp_data.password=hex_sha1($scope.password);
                    UserService.signUp($scope.signUp_data).then(function (result) {
                        if(result.code==0){
                            alert("注册成功！点击跳转登录...");
                            $rootScope.$state.go('login');
                        }else {
                            alert("注册失败："+result.msg);
                        }
                    },function (e) {
                        console.log(e);
                    })
                };

                $scope.userNameExists=function () {
                    UserService.userNameExists($scope.signUp_data.user_name)
                        .then(function (result) {
                            $scope.signUp_user_name_exists = result.code != 0;
                        },function (e) {
                            console.log(e);
                        })
                };
                $scope.$watch(function () {
                    return $scope.signUp_data;
                }, function (n,o) {
                    if(n.user_name!=o.user_name&&n.user_name!=''){
                        $scope.userNameExists();
                    }
                }, true);
            }])
        .controller('AdminController',['$scope','AdminService',
            'UserService','$rootScope',
            function ($scope,AdminService,UserService,$rootScope) {
            $scope.userName="";
                if($rootScope.$state.isLogin){
                    AdminService.getSidebar()
                        .then(function (result) {
                            $scope.sidebar_data=result.data;
                        },function (e) {
                            console.log(e);
                        });
                    UserService.checkLevel()
                        .then(function (result) {
                            $rootScope.$state.level=result.data;
                            if($rootScope.$state.level==2){
                                $rootScope.$state.go('admin.home')
                            }
                            if($rootScope.$state.level==3){
                                $rootScope.$state.go('admin.nowExam')
                            }
                        });
                }

            }])

        .controller('QuestionBankController',['$scope','ExamService','Pagination','$rootScope',
            function ($scope,ExamService,Pagination,$rootScope) {

                $scope.pagination=Pagination;
                $scope.isWhoShow=true;
                $scope.questionBankName='';
                $scope.question_bank_name_exists=false;
                $scope.isSubmit=false;
                $scope.questions={};

                $scope.questionBanks={};

                $scope.question_bank_id="";

                if($rootScope.$state.isLogin) {
                    ExamService.getQuestionBanks(1)
                        .then(function (result) {
                            if (result.code == 0) {
                                $scope.pagination.reset();
                                $scope.pagination.totalItems = result.data.totalItems;
                                $scope.questionBanks = result.data.data;
                                $scope.pagination.pageNum = Math.ceil($scope.pagination.totalItems / 8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                            } else {
                                alert(result.msg);
                            }
                        }, function (e) {
                            console.log(e);
                        });
                }

                $scope.deleteQuestion=function (questionId,index) {
                    ExamService.deleteQuestion(questionId)
                        .then(function (result) {
                            if(result.code==0){
                                alert("删除成功！");
                                $scope.questions.splice(index,1);
                            }else {
                                alert("删除失败！");
                            }
                        },function (e) {
                            console.log(e);
                        })
                };

                $scope.getPage=function (i) {
                    if(i>=1&&i<=$scope.pagination.pageNum) {
                        if(i==1){
                            ExamService.getQuestionBanks(1)
                                .then(function (result) {
                                    if (result.code == 0) {
                                        $scope.pagination.reset();
                                        $scope.pagination.totalItems = result.data.totalItems;
                                        $scope.questionBanks = result.data.data;
                                        $scope.pagination.pageNum = Math.ceil($scope.pagination.totalItems / 8);
                                        $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                                    } else {
                                        alert(result.msg);
                                    }
                                }, function (e) {
                                    console.log(e);
                                });
                        }else {
                            ExamService.getQuestionBanks(i)
                                .then(function (result) {
                                    if (result.code == 0) {
                                        $scope.pagination.pageNo = i;
                                        $scope.pagination.pageNumSort(i);
                                        $scope.questionBanks = result.data;
                                    } else {
                                        alert(result.msg);
                                    }
                                }, function (e) {
                                    console.log(e);
                                });
                        }

                    }
                };



                $scope.createQuestionBank=function () {
                    ExamService.createQuestionBank($scope.questionBankName)
                        .then(function (r) {
                            if(r.code==0){
                                ExamService.getQuestionBanks(1)
                                    .then(function (result) {
                                        if(result.code==0) {
                                            $scope.pagination.reset();
                                            $scope.pagination.totalItems=result.data.totalItems;
                                            $scope.questionBanks = result.data.data;
                                            $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                            $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                                        }else {
                                            alert("错误："+result.msg);
                                        }
                                    },function (e) {
                                        console.log(e);
                                    });
                                alert("创建成功！");
                            }else {
                                alert("创建失败："+r.msg);
                            }
                        },function (e) {
                            alert(e);
                        })
                };

                $scope.deleteQuestionBank=function (questionBankId,questionBanks,index) {
                    ExamService.deleteQuestionBank(questionBankId)
                        .then(function (result) {
                            if(result.code==0){
                                alert("删除成功！");
                                questionBanks.splice(index,1);
                                $scope.pagination.totalItems=$scope.pagination.totalItems-1;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                            }else {
                                alert("删除失败！");
                            }
                        })
                };


                $scope.pageSwitch=function (o) {
                    if(o==0){
                        ExamService.getQuestionBanks(1)
                            .then(function (result) {
                                if(result.code==0) {
                                    $scope.pagination.reset();
                                    $scope.pagination.totalItems=result.data.totalItems;
                                    $scope.questionBanks = result.data.data;
                                    $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                    $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                                    $scope.isWhoShow=true;
                                }else {
                                    alert(result.msg);
                                }
                            },function (e) {
                                console.log(e);
                            });
                    }else {
                        ExamService.getQuestions(o.question_bank_id,1)
                            .then(function (result) {
                                if(result.code==0){
                                    $scope.pagination.reset();
                                    $scope.question_bank_id=o.question_bank_id;
                                    $scope.pagination.totalItems=result.data.totalItems;
                                    $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                    $scope.pagination.defaultPageNum($scope.pagination.pageNum);

                                    $scope.questions = result.data.data;
                                    $scope.QBName=o.question_bank_name;
                                    $scope.isWhoShow=false;
                                }else {
                                    alert("错误："+result.msg);
                                }
                            },function (e) {
                                alert(e.status);
                            })
                    }
                };


                $scope.getQuestions=function (questionBankId,pageNum) {
                    ExamService.getQuestions(questionBankId,pageNum)
                        .then(function (result) {
                            if(result.code==0){

                                if( pageNum==1){
                                    $scope.pagination.reset();
                                    $scope.pagination.totalItems=result.data.totalItems;
                                    $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                    $scope.pagination.defaultPageNum($scope.pagination.pageNum);

                                    $scope.questions = result.data.data;
                                    $scope.pagination.pageNo=pageNum;
                                }else {
                                    $scope.questions=result.data;
                                    $scope.pagination.pageNo=pageNum;
                                    $scope.pagination.pageNumSort(pageNum);
                                }
                            }else {
                                alert("Unknown found")
                            }

                        },function (e) {
                            console.log(e);
                        })
                };

                $scope.questionBankNameExists=function () {
                    ExamService.questionBankNameExists($scope.questionBankName)
                        .then(function (result) {
                            $scope.question_bank_name_exists = result.code != 0;
                            $scope.isSubmit = !$scope.question_bank_name_exists;
                        },function (e) {
                            console.log(e);
                        })
                };
                $scope.$watch(function () {
                    return $scope.questionBankName;
                }, function (n,o) {
                    if(n!=o&&n!=''){
                        $scope.questionBankNameExists();
                    }
                }, true);

            }])

        .controller('QuestionsController',['$scope'
            ,'ExamService',
            function ($scope,ExamService) {
                $scope.examservice=ExamService;
                $scope.isSingle=true;
                $scope.isBatch=false;
                $scope.question_data={};


                $scope.questionBank={};
                $scope.searchWord="";
                $scope.questionType=1;
                $scope.questionDegree=1;
                $scope.question_analysis='';
                $scope.question_title='';



                $scope.tfAnswer=true;
                $scope.questionOptions=[];
                var questionBankId='';


                $scope.questionOptions[0]={
                    isTrue:true,
                    content:''
                };
                $scope.questionOptions[1]={
                    isTrue:false,
                    content:''
                };

                $scope.singleOrBatch=function (i) {
                    if(!i){
                        $scope.isSingle=!$scope.isSingle;
                        $scope.isBatch=!$scope.isBatch;
                    }
                };

                ExamService.searchQuestionBank("123",true)
                    .then(function (result) {
                        if(result.code==0) {
                            $scope.questionBank = result.data;
                            $scope.sarechPlag=false;
                        }
                    },function (e) {
                        console.log(e)
                    });

                $scope.$watch(function () {
                    return $scope.searchWord;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.searchQuestionBank(n,false)
                            .then(function (result) {
                                if(result.code==0) {
                                    $scope.questionBank = result.data;
                                }
                            },function (e) {
                                console.log(e)
                            });
                    }
                }, true);


                $scope.selectTrueOption=function (i) {
                    for(var p=0;p<$scope.questionOptions.length;p++){
                        if(p!=i)
                            $scope.questionOptions[p].isTrue=false;
                        if(p==i)
                            $scope.questionOptions[p].isTrue=true;
                    }
                };

                $scope.addQuestionOption=function () {
                    $scope.questionOptions[$scope.questionOptions.length]={
                        isTrue:false,
                        content:''
                    };
                };

                $scope.deleteQuestionOption=function (i) {
                    $scope.questionOptions.splice(i,1)
                };

                $scope.optionTF=function (i) {
                    $scope.tfAnswer = i;
                };

                $scope.optionQuestionBank=function (p) {
                    questionBankId=p.question_bank_id;
                    $scope.searchWord=p.question_bank_name;
                };

                $scope.optionQuestionType=function (i) {
                    if(i==1){
                        $scope.questionType=1;
                    }
                    if(i==2){
                        $scope.questionType=2;
                    }
                    if(i==3){
                        $scope.questionType=3;
                    }
                };

                $scope.optionQuestionDegree=function (i) {
                    if(i==1){
                        $scope.questionDegree=1;
                    }
                    if(i==2){
                        $scope.questionDegree=2;
                    }
                    if(i==3){
                        $scope.questionDegree=3;
                    }
                };

                $scope.reset=function () {
                    $scope.searchWord="";
                    $scope.questionType=1;
                    $scope.questionDegree=1;
                    $scope.questionBank={};
                    $scope.question_analysis='';
                    $scope.question_title='';


                    $scope.tfAnswer=true;
                    $scope.questionOptions=[];
                    questionBankId='';


                    $scope.questionOptions[0]={
                        isTrue:true,
                        content:''
                    };
                    $scope.questionOptions[1]={
                        isTrue:false,
                        content:''
                    };
                };

                $scope.addQuestion=function () {

                    var optionData=[];
                    var answer='';

                    if(questionBankId==''){
                        alert("请选择题库！");
                        return;
                    }

                    for(var t=0;t<$scope.questionOptions.length;t++){
                        optionData[t]=$scope.questionOptions[t].content;
                        if($scope.questionOptions[t].isTrue)
                            answer=$scope.questionOptions[t].content;
                    }

                    if($scope.questionType==1){
                        $scope.data={
                            question_title:{title:$scope.question_title,option:optionData},
                            answer:answer,
                            is_what:1,
                            question_bank_id:questionBankId,
                            question_level:$scope.questionDegree,
                            question_state:1,
                            question_analysis:$scope.question_analysis
                        };
                        ExamService.addQuestion($scope.data)
                            .then(function (result) {
                                if(result.code==0){
                                    alert("添加成功！");
                                    $scope.reset();
                                }else {
                                    alert("添加失败："+result.msg);
                                }
                            },function (e) {
                                alert(e.status)
                            });
                    }
                    if($scope.questionType==2){
                        $scope.data={
                            question_title:$scope.question_title,
                            answer:$scope.tfAnswer,
                            is_what:2,
                            question_bank_id:questionBankId,
                            question_level:$scope.questionDegree,
                            question_state:1,
                            question_analysis:$scope.question_analysis
                        };
                        ExamService.addQuestion($scope.data)
                            .then(function (result) {
                                if(result.code==0){
                                    alert("添加成功！");
                                    $scope.reset();
                                }else {
                                    alert("添加失败："+result.msg);
                                }
                            },function (e) {
                                alert(e.status)
                            });
                    }
                    if($scope.questionType==3){
                        $scope.data={
                            question_title:$scope.question_title,
                            question_bank_id:questionBankId,
                            question_level:$scope.questionDegree,
                            question_state:1,
                            is_what:3,
                            question_analysis:$scope.question_analysis
                        };
                        ExamService.addQuestion($scope.data)
                            .then(function (result) {
                                if(result.code==0){
                                    alert("添加成功！");
                                    $scope.reset();
                                }else {
                                    alert("添加失败："+result.msg);
                                }
                            },function (e) {
                                alert(e.status)
                            });
                    }
                }
            }])
        .controller('PaperController',['$scope','ExamService',
            'Pagination','$rootScope',
            function ($scope,ExamService,Pagination,$rootScope) {
                $scope.pagination=Pagination;
                $scope.isWhoShow=true;

                $scope.paperName={name:""};
                $scope.paper=[];
                $scope.questionNum=0;
                $scope.score=0;
                $scope.questionBank={};
                $scope.questions={};
                $scope.searchWord="";
                $scope.isFirst=true;
                $scope.papers={};

                var questionBankId="";
                var question={};


                if($rootScope.$state.isLogin) {
                    ExamService.getPapers(1)
                        .then(function (result) {
                            if (result.code == 0) {
                                $scope.pagination.reset();
                                $scope.pagination.totalItems = result.data.totalItems;
                                $scope.papers = result.data.data;
                                $scope.pagination.pageNum = Math.ceil($scope.pagination.totalItems / 8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                            } else {
                                alert(result.msg);
                            }
                        }, function (e) {
                            console.log(e);
                        });
                }

                $scope.getPage=function (i) {
                    if(i>=1&&i<=$scope.pagination.pageNum) {
                        if(i==1){
                            ExamService.getPapers(1)
                                .then(function (result) {
                                    if (result.code == 0) {
                                        $scope.pagination.reset();
                                        $scope.pagination.totalItems = result.data.totalItems;
                                        $scope.papers = result.data.data;
                                        $scope.pagination.pageNum = Math.ceil($scope.pagination.totalItems / 8);
                                        $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                                    } else {
                                        alert(result.msg);
                                    }
                                }, function (e) {
                                    console.log(e);
                                });
                        }else {
                            ExamService.getPapers(i)
                                .then(function (result) {
                                    if (result.code == 0) {
                                        $scope.pagination.pageNo = i;
                                        $scope.pagination.pageNumSort(i);
                                        $scope.papers = result.data;
                                    } else {
                                        alert(result.msg);
                                    }
                                }, function (e) {
                                    console.log(e);
                                });
                        }
                    }
                };

                var cleanSearchData=function () {
                    $scope.questions={};
                    $scope.searchWord="";
                    $scope.pagination.reset();
                    ExamService.searchQuestionBank("123",true)
                        .then(function (result) {
                            if(result.code==0) {
                                $scope.questionBank = result.data;
                                $scope.isFirst=false;
                            }
                        },function (e) {
                            console.log(e)
                        });
                };

                $scope.OptionQuestion=function (p) {
                    question=p;
                    cleanSearchData();
                };

                $scope.addQuestion=function (t) {
                    question.questions[question.questions.length]=t;
                };


                if($rootScope.$state.isLogin) {
                    ExamService.searchQuestionBank("123", $scope.isFirst)
                        .then(function (result) {
                            if (result.code == 0) {
                                $scope.questionBank = result.data;
                                $scope.isFirst = false;
                            }
                        }, function (e) {
                            console.log(e)
                        });
                }

                $scope.getQuestions=function (pageNum) {
                    ExamService.getQuestionByIsWhat(questionBankId,pageNum,question.question_status)
                        .then(function (result) {
                            if(result.code==0){

                                if( pageNum==1){
                                    $scope.pagination.totalItems=result.data.totalItems;
                                    $scope.questions=result.data.data;
                                    $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/5);
                                    $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                                    $scope.pagination.pageNumSort(pageNum);
                                    $scope.pagination.pageNo=pageNum;
                                }else {
                                    $scope.questions=result.data;
                                    $scope.pagination.pageNo=pageNum;
                                    $scope.pagination.pageNumSort(pageNum);
                                }
                            }else {
                                alert("Unknown found")
                            }

                        },function (e) {
                            console.log(e);
                        })
                };

                $scope.optionQuestionBank=function (q) {
                    questionBankId=q.question_bank_id;
                    $scope.searchWord=q.question_bank_name;
                    $scope.getQuestions(1);
                };

                $scope.$watch(function () {
                    return $scope.searchWord;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.searchQuestionBank(n,$scope.isFirst)
                            .then(function (result) {
                                if(result.code==0) {
                                    $scope.questionBank = result.data;
                                    $scope.isFirst=false;
                                }
                            },function (e) {
                                console.log(e)
                            });
                    }
                }, true);


                $scope.$watch(function () {
                    return $scope.paper;
                }, function () {
                    $scope.questionNum=0;
                    $scope.score=0;
                    for(var i=0;i<$scope.paper.length;i++){
                        $scope.questionNum+=$scope.paper[i].questions.length;
                        $scope.score+=$scope.paper[i].questions.length*$scope.paper[i].question_score;
                    }
                }, true);

                $scope.change=function () {
                    $scope.isWhoShow=!$scope.isWhoShow;
                };

                $scope.plus=function (i) {
                    $scope.itme=$scope.itme+i;
                };

                $scope.deleteQuestionParent=function (i) {
                    $scope.paper.splice(i,1);
                };
                $scope.deleteQuestion=function (i,o) {
                    o.splice(i,1);
                };

                $scope.addQuestionParent=function (i) {
                    i == 1 ?($scope.paper[$scope.paper.length] = {
                            question_status: 1,
                            question_name: '单选题',
                            question_score: 0,
                            questions: []
                        }) : i == 2 ? ( $scope.paper[$scope.paper.length] = {
                                question_status: 2,
                                question_name: '判断题',
                                question_score: 0,
                                questions: []
                            }) : ($scope.paper[$scope.paper.length] = {
                                question_status: 3,
                                question_name: '问答题',
                                question_score: 0,
                                questions: []
                            })
                };

                $scope.addPaper=function () {
                    for(var i=0;i<$scope.paper.length;i++){
                        if($scope.paper[i].question_status==1){
                            for(var j=0;j<$scope.paper[i].questions.length;j++){
                                $scope.paper[i].questions[j].answer="";
                            }
                        }
                        if($scope.paper[i].question_status==2){
                            for(var k=0;k<$scope.paper[i].questions.length;k++){
                                $scope.paper[i].questions[k].answer=true;
                            }
                        }
                    }
                    var data={
                        paper_score:$scope.score,
                        paper_name: $scope.paperName.name,
                        paper_question:$scope.paper
                    };
                    ExamService.createPaper(data)
                        .then(function (r) {
                            if(r.code==0){
                                alert("添加成功！");
                            }else {
                                alert("添加失败！");
                            }
                        })
                }
            }])
        .controller('CreateExamController',['$scope',
            'ExamService',
            function ($scope,ExamService) {
                $scope.examName="";
                $scope.searchPaperWord="";
                $scope.searchBranchWord="";
                $scope.paperName="";
                $scope.exam_time=60;
                var paperId="";
                $scope.papers=[];
                $scope.branchs=[];

                $scope.optionedBranch=[];
                var branchId=[];

                $scope.startDate="2016-8-5 00:00";
                $scope.endDate="2016-8-5 00:00";


                $scope.$watch(function () {
                    return $scope.searchPaperWord;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.searchPaper(n)
                            .then(function (result) {
                                $scope.papers=result.data;
                            },function (e) {
                                console.log(e);
                            });
                    }
                }, true);

                $scope.$watch(function () {
                    return $scope.searchBranchWord;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.searchBranch(n)
                            .then(function (result) {
                                $scope.branchs=result.data;
                            },function (e) {
                                console.log(e);
                            });
                    }
                }, true);

                $scope.optionPaper=function (data) {
                    $scope.paperName=data.paper_name;
                    paperId=data.paper_id;
                    $scope.searchPaperWord=data.paper_name;
                };

                $scope.optionBranch=function (data) {
                    for(var i=0;i<$scope.optionedBranch.length;i++){
                        if(data.branch_id==$scope.optionedBranch[i].branch_id){
                            return ;
                        }
                    }
                    $scope.searchBranchWord=data.branch_name;
                    $scope.optionedBranch[$scope.optionedBranch.length]=data;
                    branchId[branchId.length]=data.branch_id;
                };
                $scope.deleteOptionBranch=function (i) {
                    $scope.optionedBranch.splice(i,1);
                    branchId.splice(i,1);
                };

                $scope.addExam=function () {

                    var data={
                        exam_time:$scope.exam_time,
                        exam_name:$scope.examName,
                        paper_id:paperId,
                        exam_start_date:$scope.startDate,
                        exam_end_date:$scope.endDate,
                        branchId:branchId
                    };

                    ExamService.addExam(data)
                        .then(function (result) {
                            if(result.code==0){
                                alert("添加成功!");
                            }else {
                                alert("添加失败:"+result.msg);
                            }
                        },function (e) {
                            alert("添加失败:"+e.status);
                        })
                }
            }])
        .controller('HomeController',['$scope',
            'ExamService','$rootScope',function ($scope,ExamService,$rootScope) {
                $scope.exams=[];
                $scope.date=new Date().getTime();
                if($rootScope.$state.isLogin) {
                    ExamService.getExamByBranch()
                        .then(function (result) {
                            if (result.code == 0)
                                $scope.exams = result.data;
                        }, function (e) {
                            alert(e.status);
                        });
                }

                $scope.startExam=function (exam) {
                    $rootScope.exam=exam;
                    ExamService.getPaperById(exam.paper_id)
                        .then(function (result) {
                            if(result.code==0){
                                $rootScope.paper=result.data;
                                $rootScope.$state.go("admin.exam");
                            }else {
                                alert("失败！")
                            }
                        },function (e) {
                            alert(e.status);
                        });
                }
            }])
        .controller('ExamController',['$rootScope','$scope','ExamService',
            function ($rootScope,$scope,ExamService) {
                $scope.paper=$rootScope.paper;
                $scope.autoScoringQuestions=[];
                $scope.subjectiveQuestions=[];
                
                $scope.option=function (question,options,option) {
                    for(var i=0;i<options.length;i++){
                        options[i].span=false;
                    }
                    option.span=true;
                    question.answer=option;
                };
                $scope.optionTF=function (t,i) {
                    t.answer = i;
                };

                $scope.submit=function () {
                    $scope.autoScoringQuestions=[];
                    $scope.subjectiveQuestions=[];
                    for(var i=0;i<$scope.paper.paper_question.length;i++){
                        if($scope.paper.paper_question[i].question_status==1||$scope.paper.paper_question[i].question_status==2) {
                            for (var j = 0; j < $scope.paper.paper_question[i].questions.length; j++) {
                                $scope.autoScoringQuestions[$scope.autoScoringQuestions.length]={question_id:$scope.paper.paper_question[i].questions[j].question_id,
                                    examinee_answer:$scope.paper.paper_question[i].questions[j].answer,
                                    score:$scope.paper.paper_question[i].question_score
                                };
                            }
                        }
                        if($scope.paper.paper_question[i].question_status==3){
                            for (var k = 0; k < $scope.paper.paper_question[i].questions.length; k++) {
                                $scope.subjectiveQuestions[$scope.subjectiveQuestions.length] = {
                                    question_id: $scope.paper.paper_question[i].questions[k].question_id,
                                    examinee_answer: $scope.paper.paper_question[i].questions[k].answer,
                                    score: $scope.paper.paper_question[i].question_score,
                                    question_title: $scope.paper.paper_question[i].questions[k].question_title
                                };
                            }
                        }
                    }
                    console.log($scope.subjectiveQuestions);
                    var paper = {
                        exam_id:$rootScope.exam.exam_id,
                        autoScoringQuestions:$scope.autoScoringQuestions,
                        subjectiveQuestions:$scope.subjectiveQuestions
                    };
                    console.log(paper);
                    ExamService.submitPaper(paper)
                        .then(function (result) {
                            console.log(result.code);
                        })
                }
            }])
        .controller('BranchController',['$scope',
            'ExamService','$rootScope','Pagination',
            function ($scope,ExamService,$rootScope,Pagination) {
                $scope.pagination=Pagination;
                $scope.branches={};
                $scope.branchName="";
                $scope.isSubmit=false;
                $scope.branch_name_exists=false;
                $scope.branchId='';
                $scope.index=-1;

                if($rootScope.$state.isLogin){
                    ExamService.getBranches(1)
                        .then(function (result) {
                            if(result.code==0) {
                                $scope.pagination.reset();
                                $scope.branches = result.data.data;
                                $scope.pagination.totalItems = result.data.totalItems;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                            }
                            else {
                                alert("error:" + result.msg)
                            }
                        },function (e) {
                            alert(e.status);
                        });
                }

                $scope.optionBranch=function (branchId,index) {
                    $scope.branchId=branchId;
                    $scope.index=index;
                };

                $scope.updateBranch=function () {
                    ExamService.updateBranch($scope.branchId,$scope.branchName)
                        .then(function (result) {
                            if(result.code==0){
                                $scope.branches[$scope.index].branch_name=$scope.branchName;
                                $scope.branchId="";
                                $scope.branchName="";
                                alert("修改成功！")
                            }else {
                                $scope.branchId="";
                                $scope.branchName="";
                                alert("修改失败！"+result.msg)
                            }
                        },function (e) {
                            alert(e.data.error);
                        })
                };

                $scope.getPage=function (i) {
                    if(i>=1&&i<=$scope.pagination.pageNum) {
                        ExamService.getBranches(i)
                            .then(function (result) {
                                if (result.code == 0) {

                                    $scope.pagination.pageNo = i;
                                    $scope.pagination.pageNumSort(i);
                                    if(i==1){
                                        $scope.branches = result.data.data;
                                        $scope.pagination.totalItems = result.data.totalItems;
                                    }else {
                                        $scope.branches = result.data;
                                    }
                                } else {
                                    alert(result.msg);
                                }
                            }, function (e) {
                                console.log(e);
                            });
                    }
                };


                $scope.createBranch=function () {
                    ExamService.createBranch($scope.branchName)
                        .then(function (result) {
                            if(result.code==0){
                                alert("添加成功.");
                                ExamService.getBranches(1)
                                    .then(function (result) {
                                        $scope.pagination.reset();
                                        $scope.branches = result.data.data;
                                        $scope.pagination.totalItems = result.data.totalItems;
                                        $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                        $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                                        $scope.pagination.pageNumSort(1);
                                    },function (e) {
                                        alert(e.status);
                                    });
                                $scope.branchName="";
                            }else {
                                alert("添加失败:"+result.msg)
                            }
                        },function (e) {
                            alert(e.status)
                        })
                };

                $scope.deleteBranch=function (branchId,branches,index) {
                    ExamService.deleteBranch(branchId)
                        .then(function (result) {
                            if(result.code==0) {
                                alert("删除成功！");
                                            branches.splice(index,1);
                                            $scope.pagination.totalItems = $scope.pagination.totalItems-1;
                                            $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                            $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                            }else {
                                alert("删除失败！");
                            }
                        },function (e) {
                            alert(e.status)
                        })
                };
                $scope.$watch(function () {
                    return $scope.branchName;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.branchNameExists(n)
                            .then(function (result) {
                                $scope.branch_name_exists = result.code != 0;
                                $scope.isSubmit = !$scope.branch_name_exists;
                            })
                    }
                }, true);
            }])
        .controller('UserInfoController',['$scope','$rootScope','Pagination','UserService',
            function ($scope,$rootScope,Pagination,UserService) {
            $scope.users={};
            $scope.pagination=Pagination;
                if($rootScope.$state.isLogin){
                    UserService.queryUsersByFounderId(1)
                        .then(function (result) {
                            if(result.code==0) {
                                $scope.pagination.reset();
                                $scope.users = result.data.data;
                                $scope.pagination.totalItems = result.data.totalItems;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                            }
                            else {
                                alert("error:" + result.msg)
                            }
                        },function (e) {
                            alert(e.data.error);
                        })
                }

                $scope.getPage=function (i) {
                    if(i>=1&&i<=$scope.pagination.pageNum) {
                        UserService.queryUsersByFounderId(i)
                            .then(function (result) {
                                if (result.code == 0) {
                                    $scope.pagination.pageNo = i;
                                    $scope.pagination.pageNumSort(i);
                                    if(i==1){
                                        $scope.users = result.data.data;
                                        $scope.pagination.totalItems = result.data.totalItems;
                                    }else {
                                        $scope.users = result.data;
                                    }
                                } else {
                                    alert(result.msg);
                                }
                            }, function (e) {
                                console.log(e);
                            });
                    }
                };

                $scope.deleteUserById=function (userId,branchId,users,index) {
                    UserService.deleteUserById(userId,branchId)
                        .then(function (result) {
                            if(result.code==0){
                                alert("删除成功！");
                                users.splice(index,1);
                                $scope.pagination.totalItems = $scope.pagination.totalItems-1;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum);

                            }else {
                                alert("删除失败！");
                            }
                        },function (e) {
                            alert(e.data.error);
                        })
                }
        }])
        .controller('AddUserController',['$scope','$rootScope','Pagination','FileUploader','ExamService',
            'UserService',
            function ($scope,$rootScope,Pagination,FileUploader,ExamService,UserService) {
                if($rootScope.$state.isLogin) {
                    $rootScope.$state.go('admin.addUser.singleAddUser');
                }
            $scope.uploader= new FileUploader({
                    headers: {Authorization:'Bearer ' + $rootScope.$state.token},
                    url:'/api/users',
                    method:'post',
                    autoUpload:true,
                    queueLimit: 1,
                    removeAfterUpload:true
                });

            $scope.isSingle=true;
            $scope.isBatch=false;
            $scope.signUp_data={};
            $scope.searchBranchWord="";
            $scope.branches=[];
            $scope.passwd={password:"",passwordAgain:""};


                $scope.$watch(function () {
                    return $scope.searchBranchWord;
                }, function (n,o) {
                    if(n!=''){
                        ExamService.searchBranch(n)
                            .then(function (result) {
                                $scope.branches=result.data;
                            },function (e) {
                                console.log(e);
                            });
                    }
                }, true);

                $scope.signUp = function () {
                    $scope.signUp_data.password=hex_sha1($scope.passwd.password);
                    UserService.addUser($scope.signUp_data).then(function (result) {
                        if(result.code==0){
                            alert("添加成功！");
                        }else {
                            alert("添加失败："+result.msg);
                        }
                    },function (e) {
                        console.log(e);
                    })
                };

                $scope.optionBranch=function (data) {
                    $scope.signUp_data.branch_id=data.branch_id;
                    $scope.searchBranchWord=data.branch_name;
                };
                $scope.singleOrBatch=function (i) {
                    if(!i){
                        $scope.isSingle=!$scope.isSingle;
                        $scope.isBatch=!$scope.isBatch;
                    }
                };

                $scope.userNameExists=function () {
                    UserService.userNameExists($scope.signUp_data.user_name)
                        .then(function (result) {
                            $scope.signUp_user_name_exists = result.code != 0;
                        },function (e) {
                            console.log(e);
                        })
                };
                $scope.$watch(function () {
                    return $scope.signUp_data;
                }, function (n,o) {
                    if(n.user_name!=o.user_name&&n.user_name!=''){
                        $scope.userNameExists();
                    }
                }, true);

                $scope.fileOpen=function () {
                    document.getElementById("btn_file").click();
                };

                $scope.uploader.onAfterAddingFile = function(item) {
                    $.AMUI.progress.start();
                };

                $scope.uploader.onProgressItem = function(item, progress) {
                    $.AMUI.progress.set($scope.uploader.progress/100);
                };

                $scope.uploader.onCompleteItem = function(item, response, status, headers){
                    if(status==200){
                        if(response.code==0){
                            $.AMUI.progress.done();
                            alert("添加成功  (￢∀￢)σ");
                        }else {
                            $.AMUI.progress.done();
                            alert("添加失败,请检查上传文件。 (o´_`o)");
                        }
                    } else {
                        $.AMUI.progress.done();
                        alert("添加失败,请检查上传文件。 (o´_`o)");
                    }
                }
        }])
        .controller('FlawSweeperController',['$scope','ExamService','Pagination','$rootScope',
            function ($scope,ExamService,Pagination,$rootScope) {
            $scope.pagination=Pagination;
            $scope.isWhoShow=true;
            $scope.flawSweepers={};
            $scope.wrongQuestions={};
            $scope.examName="";

            if($rootScope.$state.isLogin) {
                ExamService.getFlawSweeper(1)
                    .then(function (result) {
                        if (result.code == 0) {
                            $scope.pagination.reset();
                            $scope.pagination.totalItems = result.data.totalItems;
                            $scope.flawSweepers = result.data.data;
                            $scope.pagination.pageNum = Math.ceil($scope.pagination.totalItems / 8);
                            $scope.pagination.defaultPageNum($scope.pagination.pageNum)
                        } else {
                            alert(result.msg);
                        }
                    }, function (e) {
                        console.log(e);
                    });
            }
            $scope.getPage=function (i) {
                if(i>=1&&i<=$scope.pagination.pageNum) {
                    ExamService.getFlawSweeper(i)
                        .then(function (result) {
                            if (result.code == 0) {
                                $scope.pagination.pageNo = i;
                                $scope.pagination.pageNumSort(i);
                                $scope.flawSweepers = result.data;
                            } else {
                                alert(result.msg);
                            }
                        }, function (e) {
                            console.log(e);
                        });
                }
            };

            $scope.pageSwitch=function (o,examId,examName) {
                if(o==0){
                    ExamService.getFlawSweeper(1)
                        .then(function (result) {
                            if(result.code==0) {
                                $scope.pagination.reset();
                                $scope.pagination.totalItems=result.data.totalItems;
                                $scope.flawSweepers = result.data.data;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum);
                                $scope.isWhoShow=true;
                            }else {
                                alert(result.msg);
                            }
                        },function (e) {
                            console.log(e);
                        });
                }else {
                    ExamService.getWrongQuestions(examId)
                        .then(function (result) {
                            if(result.code==0){
                                console.log(result.data);
                                $scope.wrongQuestions=result.data;
                                $scope.examName=examName;
                                $scope.isWhoShow=false;
                            }else {
                                alert("错误："+result.msg);
                            }
                        },function (e) {
                            alert(e.status);
                        })
                }
            };


            $scope.getQuestions=function (questionBankId,pageNum) {
                ExamService.getQuestions(questionBankId,pageNum)
                    .then(function (result) {
                        if(result.code==0){

                            if( pageNum==1){
                                $scope.pagination.reset();
                                $scope.pagination.totalItems=result.data.totalItems;
                                $scope.pagination.pageNum=Math.ceil($scope.pagination.totalItems/8);
                                $scope.pagination.defaultPageNum($scope.pagination.pageNum);

                                $scope.questions = result.data.data;
                                $scope.pagination.pageNo=pageNum;
                            }else {
                                $scope.questions=result.data;
                                $scope.pagination.pageNo=pageNum;
                                $scope.pagination.pageNumSort(pageNum);
                            }
                        }else {
                            alert("Unknown found")
                        }

                    },function (e) {
                        console.log(e);
                    })
            };
        }])
        .controller('ReportCardController',['$scope','ExamService','$rootScope',
            function ($scope,ExamService,$rootScope) {
                $scope.reportCard = {};
                if($rootScope.$state.isLogin){
                    ExamService.getReportCard()
                        .then(function (result) {
                            if(result.code==0){
                                $scope.reportCard = result.data;
                            }else {
                                alert("Error!");
                            }
                        },function (e) {
                        alert(e.data.error);
                    })
                }
        }])
        .controller('ExamInfoController',['$scope','ExamService',
            '$rootScope'
            ,function ($scope,ExamService,$rootScope) {
            $scope.examInfo={};
                if($rootScope.$state.isLogin){
                    ExamService.getExamInfo()
                        .then(function (result) {
                            if(result.code==0){
                                $scope.examInfo = result.data;
                            }else {
                                alert("Error!");
                            }
                        },function (e) {
                            alert(e.data.error);
                        })
                }
        }])
        .controller('CorrectController',['$scope','ExamService',
            '$rootScope'
            ,function ($scope,ExamService,$rootScope) {
                $scope.examInfo={};
                $scope.isWhoShow=1;
                $scope.branchCorrect={};
                $scope.userCorrect={};
                $scope.questions={};
                var examId="";
                if($rootScope.$state.isLogin){
                    ExamService.getExamInfo()
                        .then(function (result) {
                            if(result.code==0){
                                $scope.examInfo = result.data;
                            }else {
                                alert("Error!");
                            }
                        },function (e) {
                            alert(e.data.error);
                        })
                }

                $scope.open=function (i,id) {
                    if(i==1){
                        $scope.isWhoShow=i+1;
                        examId=id;
                        ExamService.queryBranchExamCorrect(id)
                            .then(function (result) {
                                if(result.code==0){
                                    $scope.branchCorrect=result.data;
                                }
                            },function (e) {
                                console.log(e);
                            })
                    }
                    if(i==2){
                        $scope.isWhoShow=i+1;
                        ExamService.queryUserExamCorrect(id)
                            .then(function (result) {
                                if(result.code==0){
                                    $scope.userCorrect=result.data;
                                }
                            },function (e) {
                                console.log(e)
                            })
                    }

                    if(i==3){
                        $scope.isWhoShow=i+1;
                        ExamService.getSubjectiveQuestions(examId,id)
                            .then(function (result) {
                                $scope.questions=result.data;
                                for(var i=0;i<$scope.questions.subjectiveQuestions.length;i++)
                                    $scope.questions.subjectiveQuestions[i].correctScore=0;
                            },function (e) {
                            console.log(e);
                        })
                    }
                };
                $scope.back=function () {
                    $scope.isWhoShow-=1;
                };

                $scope.submit=function () {
                    ExamService.submitCorrect($scope.questions)
                        .then(function (r) {
                            if(r.code==0){
                                alert("批改成功！")
                            }else {
                                alert("系统错误！")
                            }
                        },function (e) {
                            alert(e);
                        })
                }
            }])
})(angular,jQuery);
