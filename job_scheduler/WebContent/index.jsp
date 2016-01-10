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
					<md-button ng-click="showNewOrder($event)">New order</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Open order</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Save as</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Print</md-button></md-menu-item>
                </md-menu-item>
              </md-menu-content>
            </md-menu>
            <md-menu>
              <button ng-click="$mdOpenMenu()">
                View
              </button>
              <md-menu-content>
                <md-menu-item>
					<md-button ng-click="showNewOrder($event)">Show toolbar</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Show resources</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Show jobs</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Show timeline</md-button></md-menu-item>
                </md-menu-item>
              </md-menu-content>
            </md-menu>
			<md-menu>
              <button ng-click="$mdOpenMenu()">
                Order
              </button>
              <md-menu-content>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Edit</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Add job</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Delete job</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Arrange</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Lock</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Share</md-button></md-menu-item>
                </md-menu-item>
              </md-menu-content>
            </md-menu>
			<md-menu>
              <button ng-click="$mdOpenMenu()">
                Resources
              </button>
              <md-menu-content>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Add</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Delete</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Edit</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Import</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Share</md-button></md-menu-item>
                </md-menu-item>
              </md-menu-content>
            </md-menu>
			<md-menu>
              <button ng-click="$mdOpenMenu()">
                Jobs
              </button>
              <md-menu-content>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Add</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Delete</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Edit</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Import</md-button></md-menu-item>
                </md-menu-item>
				<md-menu-item>
					<md-button ng-click="showNewOrder($event)">Share</md-button></md-menu-item>
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
			<div layout="row" layout-fill style="height: 100%;">
                <div flex="33" style="background-color: blue;">
                  block1
                </div>
                <div flex="33" style="background-color: yellow;">
                  block2
                </div>
                <div flex="33" style="background-color: green;">
                  block3
			</div>
		<!-- <div class="radioDemo2 radioButtondemoMultiColumn">
		  <h2>Contact List</h2>
		    <md-divider></md-divider>
		
		    <md-radio-group ng-model="selectedIndex">
		      <div ng-repeat="person in contacts" class="row">
		        <div flex="" layout="row" layout-padding="" layout-align="center center">
		        
					<md-radio-button flex="30" ng-value="person.id" class="md-primary" style="margin:0px">
		
		            {{person.fullName}}
		
		          </md-radio-button>
		
		          <div flex="70"> 
		            {{person.title}}
		          </div>
		
		        </div>
		      </div>
		    </md-radio-group>
		
		    <md-divider></md-divider>
		    <div class="summary" flex="" layout="row" layout-align="space-between center">
		      <div flex-initial="">
		        <span class="title">Selected User</span>: <span class="md-checked">{{selectedUser()}}</span>
		      </div>
		    </div>
		</div>-->
	     <!--    <div layout="row">
	        	<div flex="20" style="background-color:red"><h4 class="md-title">Name</h4></div>
	        	<div flex="50" style="background-color:blue"><h4 class="md-title">Description</h4></div>
	        	<div flex="15" style="background-color:yellow"><h4 class="md-title">Sequence</h4></div>
	        	<div flex="15" style="background-color:green"><h4 class="md-title">Actions</h4></div>
	        </div>
	        <div layout="row" ng-repeat="job in jobs">
	        	<div flex="20" style="background-color:red;"><h6 class="md-subhead">{{job.name}}</h6></div>
	        	<div flex="50" style="background-color:blue;"><h6 class="md-subhead">{{job.description}}</h6></div>
	        	<div flex="10" style="background-color:yellow;"><h6 class="md-subhead">{{job.order}}</h6></div>
	        	<div flex="20" style="background-color:green;">
	        	
	        	</div>
	        </div>-->
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
