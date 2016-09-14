
console.log('init...');


var url = 'https://localhost:8443/webresaug/resources';

angular.module('app', [])
        .controller('servicesController', function ($scope, $http) {
            
          
          
            var clear = function(){
         
                $scope.selectedService = undefined;
                $scope.selectedServiceDate = undefined;
                $scope.dates = undefined;
                $scope.appointments = undefined;
                $scope.selectedAppointment = undefined;
                $scope.basket = undefined;
            
            };
            
            clear();

            console.log('test....');
            
            $scope.getServices = function () {
                $http.get(url + '/service', []).then(
                        function (data) {
                            console.log('success...');
                            console.log(data.data);
                           
                            $scope.services = data.data;
                            //return data.data;
                        }, 
                        
                function () {
                    //return undefined;

                });
            };
            
            $scope.services = $scope.getServices();
            
            $scope.getDatesForService = function(id){
               
                 $http.get(url + '/service/' + id + '/dates', []).then(
                        function (data) {
                            console.log('success...');
                            console.log(data.data);
                            $scope.dates = data.data;
                            
                           
                            $scope.appointments = undefined;
                            $scope.selectedAppointment = undefined;
                            //return data.data;
                        }, 
                        
                function () {
                   console.log('get dates error: ' + dates)

                });
            };
            
            $scope.getAppointmentsForDate = function(date){
                console.log('serviceDate: ' +  date);
                
                 $http.get(url + '/service/' + $scope.selectedService.id + '/' + date, []).then(
                        function (data) {
                            console.log('success...');
                            console.log(data.data);
                            $scope.appointments = data.data;
                            $scope.selectedAppointment = undefined;
                            //return data.data;
                        }, 
                        
                function () {
                   console.log('get appointments error: ' + dates)

                });
            }
            
            $scope.addToBasket = function(appointment){
             
                console.log('appointment: ' + appointment);
                
                $http.post(url + '/basket', appointment).then(
                        function (data) {
                            console.log('add to bag success...');
                            console.log(data.data);
                            //$scope.appointments = data.data;
                            //return data.data;
                            clear();
                            $scope.basket = data.data;
                        }, 
                        
                function () {
                   console.log('add to bag error: ')

                });
            }
            
            $scope.getBasket = function(){
                $http.get(url + '/basket', {}).then(
                        function (data) {
                            console.log('get basket ...');
                            console.log(data.data);
                            //$scope.appointments = data.data;
                            //return data.data;
                            clear();
                            $scope.basket = data.data;
                        }, 
                        
                function () {
                   console.log('get basket error')

                });
            };
            
            
            
            $scope.getBasket();
            
            
            $scope.deleteBasketAppointment = function(id){
                $http.delete(url + '/basket/' + id , {}).then(
                        function (data) {
                            console.log('delete basket appoitnment ...');
                            console.log(data.data);
                            //$scope.appointments = data.data;
                            //return data.data;
                            //clear();
                            $scope.basket = data.data;
                        }, 
                        
                function () {
                   console.log('get basket error')

                });
            }
            
           // $scope.dates = $scope.getDatesForService(id);

        });