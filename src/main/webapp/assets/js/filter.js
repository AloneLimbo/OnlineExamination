(function (angular) {
    var app = angular.module('OnlineExamApp');
    app.filter('numToLetter',function () {
        return function (input){
            return input==0?'A.':input==1?'B.':input==2?'C.':input==3?'D.':input==4?'E.':input==5?'F.':input==6?'G.':input==7?'H':'..';
        }
    })
        .filter('answerPrint',function () {
            return function (input) {
                if(input.is_what==1){
                    var plag=0;
                   for(var i=0;i<input.question_title.option.length;i++){
                       if(input.question_title.option[i]==input.answer){
                           plag=i;
                       }
                   }
                    return plag==0?'A.':plag==1?'B.':plag==2?'C.':plag==3?'D.':plag==4?'E.':plag==5?'F.':plag==6?'G.':plag==7?'H.':'..';
                }

                if(input.is_what==2){
                    if(input.answer=='true'){
                        return '对'
                    }else {
                        return '错'
                    }
                }
                if(input.is_what==3){
                    return '该问题没有标准答案.';
                }
            }
        })
})(angular);
