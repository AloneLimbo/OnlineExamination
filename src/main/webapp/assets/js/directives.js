(function(angular) {
    var app = angular.module('OnlineExamApp');
    app.directive('autoLeftnav',function(){
        return{
            restrict:'C',
            replace:true,
            link:function($scope,$element,$attr){
                $element.bind('click', function() {
                    if ($('.left-sidebar').is('.active')) {
                        if ($(window).width() > 1024) {
                            $('.tpl-content-wrapper').removeClass('active');
                        }
                        $('.left-sidebar').removeClass('active');
                    } else {

                        $('.left-sidebar').addClass('active');
                        if ($(window).width() > 1024) {
                            $('.tpl-content-wrapper').addClass('active');
                        }
                    }
                });
                if ($(window).width() < 1024) {
                    $('.left-sidebar').addClass('active');
                } else {
                    $('.left-sidebar').removeClass('active');
                }
            }
        };
    })
        .directive('searchBox',function(){
            return{
                restrict:'C',
                replace:true,
                link:function($scope,$element,$attr){
                    $('select').selected();
                }
            };
        })
        .directive('sidebarClick',function(){
            return{
                restrict:'C',
                replace:true,
                link:function($scope,$element,$attr){
                    $element.bind('click', function () {
                        $(this).siblings('.sidebar-nav-sub').slideToggle(200)
                            .end()
                            .find(".sidebar-nav-sub-ico").toggleClass("sidebar-nav-sub-ico-rotate");
                    });
                }
            };
        })
        .directive('dropdown',function () {
            return{
                restrict:'C',
                replace:true,
                link:function($scope,$element,$attr){
                    $(function() {
                       $element.bind('click', function () {
                            $element.dropdown('toggle');
                            return false;
                        })
                    })
                    }
            };
        })
        .directive('datetimepicker',function () {
            return{
                restrict:'C',
                replace:true,
                link:function($scope,$element,$attr){
                    $(function() {
                        $('.form-datetime-lang').datetimepicker({
                            language:  'zh-CN',
                            format: 'yyyy-mm-dd hh:ii'
                        });
                    })
                }
            };
        })


})(angular,jQuery);