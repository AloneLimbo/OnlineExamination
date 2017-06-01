(function(angular) {
    angular.module('OnlineExamApp')
        .service('UserService',function ($http,$rootScope) {
            return{
                login : function(login_data) {

                    return $http.post('/user/login',
                        {
                            user_name: login_data.user_name,
                            password: hex_sha1(login_data.password)
                        })
                        .then(function (response) {
                            return response.data;
                        });
                },
                signUp : function (signUp_data) {

                    return $http.post('/api/user', angular.toJson(signUp_data))
                        .then(function (response) {
                            return response.data;
                        });
                },
                addUser : function (signUp_data) {
                    return $http.post('/api/addUser', angular.toJson(signUp_data))
                        .then(function (response) {
                            return response.data;
                        });
                },
                userNameExists : function (user_name) {

                    return $http.get('/api/userName/exists/' + user_name)
                        .then(function (response) {
                            return response.data;
                        });
                },
                checkLevel : function () {
                    if($rootScope.$state.isLogin) {
                        return $http.get("/api/checkLevel")
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                queryUsersByFounderId : function (offset) {
                    return $http.get('/api/users/'+offset)
                        .then(function (r) {
                            return r.data;
                        })
                },
                deleteUserById :function (userId,branchId) {
                    return $http.delete('/api/user/'+userId+'/'+branchId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                getUserName :function () {
                    return $http.get('/api/userName')
                        .then(function (r) {
                            return r.data;
                        })
                }
            }
        })
        .service('AdminService',function ($http,$rootScope) {
            return{
                getSidebar : function () {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/sidebar')
                            .then(function (r) {
                                return r.data;
                            })
                    }
                }
            }
        })
        .service('ExamService',function ($http,$rootScope) {
            return{

                deleteQuestion : function (questionId) {
                    return $http.delete('/api/question/'+questionId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                getExamInfo : function () {
                    return $http.get('/api/examInfo')
                        .then(function (r) {
                            return r.data;
                        })
                },
                questionBankNameExists:function (name) {
                        return $http.get('/api/questionBankName/exists/' + name)
                            .then(function (r) {
                                return r.data;
                            })
                },
                createQuestionBank : function (question_bank_name) {
                        return $http.post('/api/questionBank',
                            {
                                question_bank_name: question_bank_name
                            })
                            .then(function (r) {
                                return r.data;
                            })

                },
                getQuestionBanks : function (i) {
                            return $http.get('/api/questionBank/'+i)
                                .then(function (r) {
                                    return r.data;
                                })
                },
                getQuestions : function (questionBankId,isFirstPage) {
                    if($rootScope.$state.isLogin) {
                        if (isFirstPage == 1) {
                            return $http.get('/api/questions/' + questionBankId, {headers: {isFirst: 1}})
                                .then(function (r) {
                                    return r.data;
                                })
                        } else {
                            return $http.get('/api/questions/' + questionBankId, {headers: {isFirst: isFirstPage}})
                                .then(function (r) {
                                    return r.data;
                                })
                        }
                    }
                },
                getQuestionByIsWhat : function (questionBankId,isFirstPage,isWhat) {
                    if($rootScope.$state.isLogin) {
                        if (isFirstPage == 1) {
                            return $http.get('/api/questions/' + questionBankId + '/' + isWhat, {headers: {isFirst: 1}})
                                .then(function (r) {
                                    return r.data;
                                })
                        } else {
                            return $http.get('/api/questions/' + questionBankId + '/' + isWhat, {headers: {isFirst: isFirstPage}})
                                .then(function (r) {
                                    return r.data;
                                })
                        }
                    }
                },
                addQuestion : function (data) {
                    if($rootScope.$state.isLogin) {
                        return $http.post('/api/question', angular.toJson(data))
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                searchQuestionBank : function (search,isFirst) {
                    if($rootScope.$state.isLogin) {
                        if (isFirst) {
                            return $http.get('/api/questionBank/search/search', {headers: {isFirst: 1}})
                                .then(function (r) {
                                    return r.data;
                                })
                        } else {
                            return $http.get('/api/questionBank/search/' + search, {headers: {isFirst: 0}})
                                .then(function (r) {
                                    return r.data;
                                })
                        }
                    }
                },
                createPaper : function (data) {
                    if ($rootScope.$state.isLogin) {
                        return $http.post('/api/paper', angular.toJson(data))
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                searchPaper : function (word) {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/paper/search/'+word)
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                searchBranch : function (word) {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/branch/search/'+word)
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                addExam :function (data) {
                    if ($rootScope.$state.isLogin) {
                        return $http.post('/api/exam', angular.toJson(data))
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                getExamByBranch : function () {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/exam')
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                getPaperById : function (paperId) {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/paper/' + paperId)
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                getBranches : function (offset) {
                    if ($rootScope.$state.isLogin) {
                        return $http.get('/api/branches/'+offset)
                            .then(function (r) {
                                return r.data;
                            })
                    }
                },
                createBranch :function (branch_name) {
                    return $http.post('/api/branch', {branch_name: branch_name})
                        .then(function (r) {
                            return r.data;
                        })
                },
                branchNameExists :function (branch_name) {
                    return $http.get('/api/branch/exists/'+branch_name)
                        .then(function (r) {
                            return r.data;
                        })
                },
                deleteBranch : function (branchId) {
                    return $http.delete("/api/branch/"+branchId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                updateBranch :function (branchId,branchName) {
                    return $http.put('/api/branch',{branch_id:branchId,branch_name:branchName})
                        .then(function (r) {
                            return r.data;
                        })
                },
                deleteQuestionBank : function (questionBankId) {
                    return $http.delete("/api/questionBank/"+questionBankId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                submitPaper : function (paper) {
                    return $http.post("/api/user/paper",angular.toJson(paper))
                        .then(function (r) {
                            return r.data;
                        })
                },
                getFlawSweeper : function (offset) {
                    return $http.get("/api/flawSweeper/"+offset)
                        .then(function (r) {
                            return r.data;
                        })
                },
                getWrongQuestions : function (examId) {
                    return $http.get("/api/flawSweeper/wrongQuestions/"+examId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                getReportCard : function () {
                    return $http.get("/api/reportCard")
                        .then(function (r) {
                            return r.data;
                        })
                },
                getPapers : function (i) {
                    return $http.get("/api/papers/"+i)
                        .then(function (r) {
                            return r.data;
                        })
                },
                queryBranchExamCorrect :function (examId) {
                    return $http.get("/api/branchCorrect/"+examId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                queryUserExamCorrect :function (branchId) {
                    return $http.get("/api/userCorrect/"+branchId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                getSubjectiveQuestions :function (examId,founderId) {
                    return $http.get("/api/subjectiveQuestions/"+examId+'/'+founderId)
                        .then(function (r) {
                            return r.data;
                        })
                },
                submitCorrect :function (correctData) {
                    return $http.post("/api/correct",angular.toJson(correctData))
                        .then(function (r) {
                            return r.data;
                        })
                }
            }
        })
        .service('Pagination',function () {
            var me=this;
            me.isFirst=-1;
            me.pageNum=0;
            me.totalItems=0;
            me.page=[];
            me.pageNo=1;

            me.reset=function () {
                me.isFirst=-1;
                me.pageNum=0;
                me.totalItems=0;
                me.page=[];
                me.pageNo=1;
            };

            me.defaultPageNum=function (num) {
                me.page=[];
                if(num<=5){
                    for(var i=1;i<=num;i++){
                        me.page[i-1]=i;
                    }
                }else {
                    for(var j=1;j<=5;j++){
                        me.page[j-1]=j;
                    }
                }
            };

            me.pageNumSort=function (i) {
                console.log(me.pageNum);
                if(me.totalItems>5){
                    if(i>=3&&i<=(me.pageNum-2)){
                        me.page[0]=i-2;
                        me.page[1]=i-1;
                        me.page[2]=i;
                        me.page[3]=i+1;
                        me.page[4]=i+2;
                    }
                    if(i==me.page[0]&&i>1){
                        me.page[0]=i-1;
                        me.page[1]=i;
                        me.page[2]=i+1;
                        me.page[3]=i+2;
                        me.page[4]=i+3;
                    }
                    if(i==me.page[4]&&i<me.pageNum){
                        me.page[0]=i-3;
                        me.page[1]=i-2;
                        me.page[2]=i-1;
                        me.page[3]=i;
                        me.page[4]=i+1;
                    }
                }

            };
        })
})(angular,window);

