/**
 * Created by Mahmoud Salah on 8/8/2018.
 */

'use strict';

var app = angular.module('foodChain', ['ui.router'])
    .config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider.state('main',
            {
                url:'/',
                templateUrl: "templates/main.html",
                controller: 'mainController',
                params:{
                    mainObj:null
                }
            }).state('menu',
            {
                url:'/menu',
                templateUrl: "templates/menu.html",
                controller:'menuController',
                params:{
                    menuObj:null
                }
            }).state('login',
            {
                url:'/login',
                templateUrl : 'templates/login.html',
                controller:'loginController',
                params: {
                    loginObj:null
                }
            }).state('signup',
            {
                url:'/signup',
                templateUrl : 'templates/signup.html',
                controller:'signUpController',
                params: {
                    signUpObj:null
                }
            }).state('checkOut',
            {
                url:'/check_out',
                templateUrl:'templates/checkOut.html',
                controller:'checkOutController',
                params:{
                    cartObj:null
                }
            }).state('help',
            {
                url:'/help',
                templateUrl:'templates/help.html',
                controller:'helpController',
                params:{
                    helpObj:null
                }
            });
    });

app.controller('mainController', function ($scope, $state, $stateParams, $http) {
    $scope.isLogged = false;
    $scope.userName = "";
    $scope.token = "";
    if($stateParams.mainObj === null){
        $scope.isLogged = false;
        console.log($scope.isLogged);
    }
    else
    {
        $scope.isLogged = true;
        $scope.userName = $stateParams.mainObj.username;
        $scope.token = $stateParams.mainObj.token;
    }

    $scope.menuRequest = function () {
        if($scope.userName === "" && $scope.token === "")
            $state.go('menu');
        else
            $state.go('menu',{menuObj:{username:$scope.userName, token:$scope.token}});
    };
    $scope.goMain = function () {
        $state.go('main', {menu:'main'});
        console.log($stateParams);
    };

    $scope.login = function () {
        console.log("Log in");
        $state.go('login');
    };

    $scope.signUp = function () {
        $state.go('signup');
    };

    $scope.help = function () {
        $state.go('help',{helpObj:{username:$scope.userName, token:$scope.token}});
    };

    $scope.signOut = function () {
        $state.go('main');
    };
});

app.controller('loginController', function ($stateParams, $scope, $state, $http) {
    $scope.userName = "";
    $scope.password = "";
    console.log($scope);
    $scope.submitLogin = function () {
        if($scope.userName !== null && $scope.password !== null) {
            var token ="Basic " + btoa($scope.userName + ":" + $scope.password).toString();
            console.log(token.toString());
            $http({method:"GET",url:"http://localhost:8880/PizzaMonster/user/login",headers:{'Authorization':token}})
                .then(function Success(response){
                    $state.go('main',{mainObj:{username:$scope.userName, token:token}});
            },function Error(response){
               console.log(response.statusText);
            });

        }
    };

    $scope.signOut = function () {
        $state.go('main');
    };

});

app.controller('menuController', function ($stateParams, $scope, $state, $http) {

    $scope.isLogged = false;
    $scope.userName = "";
    $scope.token = "";
    $scope.totalPrice = 0;
    $scope.cartItemsNo = 0;
    if($stateParams.menuObj === null){
        $scope.isLogged = false;
        console.log($scope.isLogged);
    }
    else
    {
        $scope.isLogged = true;
        $scope.userName = $stateParams.menuObj.username;
        $scope.token = $stateParams.menuObj.token;
    }
    console.log($scope.token);
    console.log($scope.userName);

    $http({method:"GET",url:"http://localhost:8880/PizzaMonster"}).then(function Success(response){
        $scope.menuItems = response.data;
        console.log($scope.menuItems)
    },function Error(response){
        $scope.menuItems = response.statusText;
    });
    $scope.cartItems = [];
    
    $scope.addToCart = function (menuItem) {
        console.log(menuItem);
        var index = -1;
        for (var i=0; i< $scope.cartItems.length; i = i+1)
        {
            if(menuItem.id === $scope.cartItems[i].id)
            {
                index = i;
                break;
            }
        }
        console.log(index);
        if(index === -1)
            $scope.cartItems.push({id:menuItem.id,name:menuItem.name, description:menuItem.description, price:menuItem.price, amount:1});
        else {
            var order = $scope.cartItems[index];
            console.log(order);
            $scope.cartItems.splice(index, 1);
            order.amount = order.amount + 1;
            $scope.cartItems.splice(index,0,order);
        }
        $scope.totalPrice = $scope.totalPrice + menuItem.price;
        $scope.cartItemsNo = $scope.cartItemsNo + 1;
        console.log($scope.cartItems);
    };

    $scope.checkOut = function () {
        console.log("ana hena");
        $state.go('checkOut',{cartObj:{username:$scope.userName,token:$scope.token,cartItems:$scope.cartItems, totalPrice:$scope.totalPrice, amount:$scope.cartItemsNo}});

    };

    $scope.increaseItem = function (menuItem) {
        var index = $scope.cartItems.indexOf(menuItem);
        $scope.cartItems[index].amount = $scope.cartItems[index].amount + 1;
        $scope.cartItemsNo = $scope.cartItemsNo + 1;
        $scope.totalPrice = $scope.totalPrice + menuItem.price;
    };

    $scope.decreaseItem = function (menuItem) {
        var index = $scope.cartItems.indexOf(menuItem);
        if($scope.cartItems[index].amount ===1)
            $scope.cartItems.splice(index, 1);
        else
            $scope.cartItems[index].amount = $scope.cartItems[index].amount - 1;

        $scope.cartItemsNo = $scope.cartItemsNo - 1;
        $scope.totalPrice = $scope.totalPrice - menuItem.price;
    };

    $scope.signOut = function () {
        $state.go('main');
    };
});


app.controller('signUpController', function ($stateParams, $scope, $state,$http) {
    $scope.usrName = "";
    $scope.phoneNo = "";
    $scope.password = "";
    $scope.repeatedPassword = "";
    $scope.city = "";
    $scope.streetAddress = "";
    
    $scope.submitSignUp = function () {
        console.log($scope.usrName);
        if($scope.usrName !== "" && $scope.phoneNo !== "" && $scope.password !== "" && $scope.repeatedPassword !== ""
            && $scope.city !== "" && $scope.streetAddress !== "")
        {
            console.log("eh");
            if($scope.password === $scope.repeatedPassword) {

                var userObj = {"userName": $scope.usrName,"password":$scope.password,
                    "phoneNo":$scope.phoneNo,"city":$scope.city,"streetAddress":$scope.streetAddress};
                $http({method:"POST",url:"http://localhost:8880/PizzaMonster",data:userObj})
                    .then(function Success(response){
                        console.log(response.data);
                        var token ="Basic " + btoa($scope.usrName + ":" + $scope.password).toString();
                        $state.go('main',{mainObj:{username:$scope.usrName, token:token}});
                },function Error(response){
                    console.log(response.statusText);
                });
            }
        }

    };

    $scope.signOut = function () {
        $state.go('main');
    };
});

app.controller('checkOutController', function ($stateParams, $scope, $state,$http) {
    if($stateParams.cartObj !== null) {
        if($stateParams.cartObj.username === "" && $stateParams.cartObj.token === "") {
            if($stateParams.cartObj.cartItems !== null)
                $state.go("login",{loginObj:{cartItems:$stateParams.cartObj.cartItems, totalPrice:$stateParams.cartObj.totalPrice}});
        }
        else {
            $scope.isLogged = true;
            $scope.userName = $stateParams.cartObj.username;
            $scope.token = $stateParams.cartObj.token;
            $scope.cartItems = $stateParams.cartObj.cartItems;
            $scope.totalPrice = $stateParams.cartObj.totalPrice;
            $scope.cartItemsNo = $stateParams.cartObj.amount;
        }
    }
    else
        $state.go("login");
    console.log($scope.cartItems);

    $scope.submitOrder = function () {
        var newOrder = {username:$scope.userName,totalPrice:$scope.totalPrice,items:[]};
        for(var i=0;i<$scope.cartItems.length;i++)
        {
            newOrder.items.push({item:{id:$scope.cartItems[i].id},quantity:$scope.cartItems[i].amount});
        }
        $http({method:"POST",url:"http://localhost:8880/PizzaMonster/user/submitOrder",headers:{'Authorization':$scope.token},data:newOrder})
            .then(function Success(response){
                console.log(response.data);
                // var token ="Basic " + btoa($scope.usrName + ":" + $scope.password).toString();
                $state.go('main',{mainObj:{username:$scope.userName, token:$scope.token}});
            },function Error(response){
                console.log(response.statusText);
            });
    };

    $scope.signOut = function () {
        $state.go('main');
    };
});

app.controller('helpController', function ($stateParams, $scope, $state,$http) {
    $scope.complainText = "";
    $scope.isLogged = false;
    $scope.userName = "";
    $scope.token = "";
    if($stateParams.helpObj === null){
        $scope.isLogged = false;
        console.log($scope.isLogged);
    }
    else
    {
        $scope.isLogged = true;
        $scope.userName = $stateParams.helpObj.username;
        $scope.token = $stateParams.helpObj.token;
    }


    $scope.submitComplain = function () {
        var url = "http://localhost:8880/PizzaMonster/user/complain?username="+$scope.userName;
        $http({method:"POST",url:url,headers:{'Authorization':$scope.token},
            data:{complainText:$scope.complainText}})
            .then(function Success(response){
                console.log(response.data);
            },function Error(response){
                console.log(response.statusText);
            });
    };

    $scope.signOut = function () {
        $state.go('main');
    };

});