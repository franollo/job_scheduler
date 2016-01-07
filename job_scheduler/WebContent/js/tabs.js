var app = angular.module('myApp', ['ui.bootstrap']);
  app.controller('hideButton', ['$scope', function($scope) 
  {
    $scope.tabs = [];
    $scope.timelines = [];
    $scope.visible = false;
    $scope.boolean = false;
    $scope.numOfAct = 0;
    $scope.activeNo = 0;
    $scope.leftHidden = false;
    $scope.rightHidden = false;

      var container = document.getElementById("toggle-left");

      var items = new vis.DataSet([
      /*  {id: 1, content: 'item 1', start: '2013-04-20', end: '2013-04-21', group: 1},
        {id: 2, content: 'item 2', start: '2013-04-20', end: '2013-04-22', group: 2},
        {id: 3, content: 'item 3', start: '2013-04-20', end: '2013-04-23', group: 3},
        {id: 4, content: 'item 4', start: '2013-04-20', end: '2013-04-22', group: 4},*/
        {id: 5, content: 'item 5', start: '2013-04-20', end: '2013-04-24', group: 5},
        {id: 6, content: 'item 6', start: '2013-04-20', end: '2013-04-22', group: 6},
        {id: 7, content: 'item 7', start: '2013-04-22', end: '2013-04-23', group: 6}
      ]);

      var items2 = new vis.DataSet([
        {id: 1, content: 'item 1', start: '2013-04-20', end: '2013-04-21', group: 1},
        {id: 2, content: 'item 2', start: '2013-04-20', end: '2013-04-22', group: 2},
        {id: 3, content: 'item 3', start: '2013-04-20', end: '2013-04-23', group: 3},
        {id: 4, content: 'item 4', start: '2013-04-20', end: '2013-04-22', group: 4},
      ]);

      var groups = new vis.DataSet([
        {id: 1, content: 'M1'},
        {id: 2, content: 'M2'},
        {id: 3, content: 'M3'},
        {id: 4, content: 'M4'},
        {id: 5, content: 'M5'},
        {id: 6, content: 'M6'}
      ]);

      var options = {
        height: '100%',
        editable: true,
        orientation: 'top',
        margin: 0
      };
    //timeline = new vis.Timeline(container, items, groups, options);
    var addTab = function () {
      var mydate = new Date();
      $scope.tabs.push({ title: mydate.toDateString(), content: mydate.toString()});
      $scope.tabs[$scope.tabs.length - 1].active = true;
      $scope.tabs[$scope.tabs.length - 1].data = new vis.DataSet([
        {id: 1, content: 'item 1', start: '2013-04-20', end: '2013-04-21', group: 1},
        {id: 2, content: 'item 2', start: '2013-04-20', end: '2013-04-22', group: 2},
        {id: 3, content: 'item 3', start: '2013-04-20', end: '2013-04-23', group: 3},
        {id: 4, content: 'item 4', start: '2013-04-20', end: '2013-04-22', group: 4},
      ]);
      //timeline.setItems($scope.tabs[$scope.tabs.length - 1].data);
      $scope.visible = true;
      doSth();
    };

    var removeTab = function (event, index) {
      if(index < $scope.activeNo || index == $scope.tabs.length -1) {
        $scope.activeNo = $scope.activeNo - 1;
      }
     // timeline.setItems(items);
      event.preventDefault();
      event.stopPropagation();
      $scope.tabs.splice(index, 1);
      if($scope.tabs.length == 0)
      {
        $scope.visible = false;
        $scope.activeNo = 0;
      }
    };

    var doSth = function () {
      $scope.boolean = !$scope.boolean;
      $scope.numOfAct = $scope.tabs.length;
      var length = $scope.tabs.length 
      for (i = 0; i < length; i++) {
        if ($scope.tabs[i].active == true) {
          $scope.activeNo = i;
        } 
      }
      timeline.setItems($scope.tabs[$scope.activeNo].data);
      //console.log($scope.tabs); 
    }

    var toggleRight = function () {
     // timeline.fit(true);
      if($scope.rightHidden == true && $scope.leftHidden == true)
      {
        $("#toggle-right").animate({width: "100%", opacity: '1'});
      }
      if($scope.rightHidden == true && $scope.leftHidden == false)
      {
        $("#toggle-right").animate({width: "30%", opacity: '1'});
        $("#toggle-left").animate({width: "70%", opacity: '1'});
      }
      if($scope.rightHidden == false && $scope.leftHidden == false)
      {
        $("#toggle-right").animate({width: "0%", opacity: '0'});
        $("#toggle-left").animate({width: $("#my-timeline").width() + 5, opacity: '1'});
      }
      if($scope.rightHidden == false && $scope.leftHidden == true)
      {
        $("#toggle-right").animate({width: "0%", opacity: '0'});
      }
      $scope.rightHidden = !$scope.rightHidden;
    }

    var toggleLeft = function () {
     // timeline.fit(true);
      if($scope.leftHidden == true && $scope.rightHidden == true)
      {
        $("#toggle-left").animate({width: $("#my-timeline").width() + 5, opacity: '1' });
        //$("#drag-bar").hide();
      }      
      if($scope.leftHidden == true && $scope.rightHidden == false)
      {
        $("#toggle-right").animate({width: "30%", opacity: '1'});
        $("#toggle-left").animate({width: "70%", opacity: '1'});
      }
      if($scope.leftHidden == false && $scope.rightHidden == false)
      {
        $("#toggle-right").animate({width: "100%", opacity: '1'});
        $("#toggle-left").animate({width: "0%", opacity: '0'});
      }
      if($scope.leftHidden == false && $scope.rightHidden == true)
      {
        $("#toggle-left").animate({width: "0%", opacity: '0'});
      }
      $scope.leftHidden = !$scope.leftHidden;
    }

    var limits = {
        min:  100,
        max:  $("#my-timeline").width() - 100
    };

   $('#drag-bar').mousedown(function(e){
       
        e.preventDefault();
        $(document).mousemove(function(e){
          var newX = e.pageX;
          if(newX >= limits.min && newX <= limits.max) {
            $('#position').html(e.pageX +', '+ e.pageY);
            $('#toggle-left').css("width",e.pageX+2);
            $('#toggle-right').css("width",$("#my-timeline").width()-(e.pageX+2));
          }
       })
    });
   $(document).mouseup(function(e){
       $(document).unbind('mousemove');
       });

   $(window).resize(function(){
        $('#toggle-right').css("width",$("#my-timeline").width() - $("#toggle-left").width());
        limits = {
        min:  50,
        max:  $("#my-timeline").width() - 50
    };
   });

    $scope.addTab    = addTab;
    $scope.removeTab = removeTab;
    $scope.doSth = doSth;
    $scope.toggleLeft = toggleLeft;
    $scope.toggleRight = toggleRight;

  /*  var openVis = function () {
      
    }
*/
  }]);