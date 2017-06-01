(function(window, angular) {
    angular.module('OnlineExamApp',['ui.router','angularFileUpload'])
        .run(['$rootScope', '$state', '$stateParams',
            function($rootScope, $state, $stateParams){
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.$state.isLogin = false;
                $rootScope.$state.token="";
                $rootScope.$state.level=0;
                $rootScope.paper={};
                $rootScope.exam={};
                $rootScope.userName="";
            }])
        .config(['$stateProvider',
            '$urlRouterProvider',function ($stateProvider,$urlRouterProvider) {
                $urlRouterProvider.otherwise("login");

            $stateProvider
                .state('admin',{
                    url:'/admin',
                    templateUrl:'/templets/admin.html',
                    controller:function ($rootScope) {
                        // console.log("admin....");
                        if($rootScope.$state.isLogin==false){
                            console.log("admin....login");
                            $rootScope.$state.go('login')
                        }else {
                                $rootScope.$state.go('admin')
                        }
                    }
                })
                .state('admin.home',{
                    url:'/home',
                    templateUrl:'/templets/home.html'
                })
                .state('admin.nowExam',{
                    url:'/nowExam',
                    templateUrl:'/templets/nowExam.html'
                })
                .state('login',{
                    url:'/login',
                    templateUrl:'/templets/login.html'
                })

                .state('admin.createExam',{
                    url:'/createExam',
                    templateUrl:'/templets/createExam.html'
                })
                .state('admin.addQuestions',{
                    url:'/addQuestions',
                    templateUrl:'/templets/addQuestions.html'
                })
                .state('admin.questionBank',{
                    url:'/questionBank',
                    controller:function ($rootScope) {
                        if($rootScope.$state.level==3){
                            alert("不要瞎搞！！");
                        }
                    },
                    templateUrl:'/templets/questionBank.html'
                })
                .state('admin.paperBank',{
                    url:'/paperBank',
                    templateUrl:'/templets/paperBank.html'
                })
                .state('admin.exam',{
                    url:'/exam',
                    templateUrl:'/templets/exam.html'
                })
                .state('admin.branchEdit',{
                    url:'/branchEdit',
                    templateUrl:'/templets/branchEdit.html'
                })
                .state('admin.addUser',{
                    url:'/addUser',
                    templateUrl:'/templets/addUser.html'
                })
                .state('admin.addUser.batchAddUser',{
                    url:'/batchAddUser',
                    templateUrl:'/templets/batchAddUser.html'
                })
                .state('admin.addUser.singleAddUser',{
                    url:'/singleAddUser',
                    templateUrl:'/templets/singleAddUser.html'
                })
                .state('admin.userInfo',{
                    url:'/userInfo',
                    templateUrl:'/templets/userInfo.html'
                })
                .state('admin.EcharTest',{
                    url:'/EcharTest',
                    templateUrl:'/templets/EcharTest.html'
                })
                .state('admin.flawSweeper',{
                    url:'/flawSweeper',
                    templateUrl:'/templets/flawSweeper.html'
                })
                .state('admin.reportCard',{
                    url:'/reportCard',
                    templateUrl:'/templets/reportCard.html'
                })
                .state('admin.examInfo',{
                    url:'/examInfo',
                    templateUrl:'/templets/examInfo.html'
                })
                .state('admin.correct',{
                    url:'/correct',
                    templateUrl:'/templets/correct.html'
                })


            }])

})(window, angular, jQuery);