<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="bower_components/angular-material/angular-material.css">
    <link href="vis/vis.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/angular_style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  </head>
  <body>
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-aria/angular-aria.js"></script>
    <script src="bower_components/angular-material/angular-material.js"></script>
    <script src="vis/vis.js"></script>
     <script src="js/main_controller.js"></script>
      <script src="js/auth.js"></script>
      <script src="js/data.js"></script>
      <script type="text/javascript" src="bower_components/ng-sortable/dist/ng-sortable.min.js"></script>
<link rel="stylesheet" type="text/css" href="bower_components/ng-sortable/dist/ng-sortable.min.css">

    <div ng-controller="DemoBasicCtrl as ctrl" ng-cloak="" ng-app="MyApp">
    <md-toolbar class="md-menu-toolbar my-menu">
      <div layout="row">
        <md-toolbar-filler layout="" layout-align="center center">
          <i class="material-icons md-48">sort</i>
        </md-toolbar-filler>

        <div>
          <div class="md-toolbar-tools">{{orderTitle}}</div>
          <md-menu-bar>
            <md-menu>
              <button ng-click="$mdOpenMenu()">
                File
              </button>
              <md-menu-content>
                <md-menu-item>
                  <md-menu>
                    <md-button ng-click="$mdOpenMenu()">New</md-button>
                    <md-menu-content>
                      <md-menu-item><md-button ng-click="showNewOrder($event)">Production order</md-button></md-menu-item>
                    </md-menu-content>
                  </md-menu>
                </md-menu-item>
                <md-menu-item>
                <i class="material-icons">accessibility</i>
                  <md-button ng-click="ctrl.sampleAction('Open', $event)">
                    Open...
                    <span class="md-alt-text"> {{ 'M-O' | keyboardShortcut }}</span>
                  </md-button>
                </md-menu-item>
                <md-menu-item>
                <i class="material-icons">code</i>
                  <md-button disabled="disabled" ng-click="ctrl.sampleAction('Rename', $event)">
                    Rename
                  </md-button>
                </md-menu-item>
                <md-menu-divider></md-menu-divider>
                <md-menu-item>
                  <md-button ng-click="ctrl.sampleAction('Print', $event)">
                    Print
                    <span class="md-alt-text">{{ 'M-P' | keyboardShortcut }}</span>
                  </md-button>
                </md-menu-item>
              </md-menu-content>
            </md-menu>
       
          </md-menu-bar>
        
        </div>
        <span flex></span>
        <div layout="" layout-align="end end">
          <md-button class="md-raised md-primary"  ng-click="showSignIn()" ng-hide="authenticated">
        	Sign in
      	  </md-button>
      	  <md-button class="md-accent md-primary" ng-click="logout()" ng-show="authenticated">
 			Sign out     
 		  </md-button>	  
      	</div>
      </div>
    </md-toolbar>
     <md-toolbar class="my-toolbar" ng-hide="isSmall">
      <div class="md-toolbar-tools">
        <div id="button" ng-click="isCollapsed = !isCollapsed; isSmall = !isSmall">
          <i class="material-icons md-18">{{isCollapsed ? 'lock_open' : 'lock_outline'}}</i>
        </div>
        <div id="button" ng-click = "getThem()">
          <i class="material-icons md-18">redo</i>
        </div>
        <div id="button">
          <i class="material-icons md-18">undo</i>
        </div>
        <div id="button" ng-click="showAddJob($event)">
          <i class="material-icons md-18">add</i>
        </div>
      </div>
    </md-toolbar>
    <div id="page-container" ng-class="{'small':isSmall, 'big':!isSmall}">
      <div id="workspace" class="tabsdemoStaticTabs">
          <md-tabs class="md-accent" md-align-tabs="bottom" md-stretch-tabs='always'>
           <md-tab id="tab1">
        <md-tab-label>Timeline</md-tab-label>
        <md-tab-body>
          <div id="my-timeline">
          </div>
        </md-tab-body>
      </md-tab>
        <md-tab id="tab2">
        <md-tab-label>Jobs</md-tab-label>
        <md-tab-body>
          {{penis}}
        </md-tab-body>
      </md-tab>
                       <md-tab id="tab2">
        <md-tab-label>Resources</md-tab-label>
        <md-tab-body>
              <h1 class="md-title">Add new job</h1>
    <div layout="row">
      
    </div>
        </md-tab-body>
      </md-tab>
          </md-tabs>
      </div>
  </div>
    <div id="footer">
    	<div ng-hide="authenticated">
    		You are not logged in
    	</div>
    	<div ng-show="authenticated">
    		Welcome {{nam}} {{sur}} ({{log}})
    	</div>
    </div>
  </div>
  </body>

</html>
