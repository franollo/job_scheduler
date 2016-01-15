<!DOCTYPE html>
<html>
  	<head>
	    <link rel="stylesheet" href="bower_components/angular-material/angular-material.css">
	    <link href="vis/vis.css" rel="stylesheet" type="text/css"/>
	    <link rel="stylesheet" href="css/angular_style.css">
	    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	    <link rel="stylesheet" type="text/css" href="bower_components/ng-sortable/dist/ng-sortable.min.css">
  	</head>
	<body>
		<script src="bower_components/angular/angular.js"></script>
		<script src="bower_components/angular-animate/angular-animate.js"></script>
		<script src="bower_components/angular-aria/angular-aria.js"></script>
		<script src="bower_components/angular-material/angular-material.js"></script>
		<script src="vis/vis.js"></script>
		<script src="js/main_controller.js"></script>
		<script src="js/dialogs.js"></script>
		<script src="js/auth.js"></script>
		<script src="js/data.js"></script>
		<script type="text/javascript" src="bower_components/ng-sortable/dist/ng-sortable.min.js"></script>

	    <div id="cialo" style="overflow:hidden" ng-controller="DemoBasicCtrl as ctrl" ng-cloak="" ng-app="MyApp">
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
							<md-button ng-click="openOrder($event)">Open order</md-button></md-menu-item>
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
							<md-button ng-click="showAddJob($event)">Add job</md-button></md-menu-item>
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
							<md-button ng-click="showAddResource($event)">Add</md-button></md-menu-item>
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
							<md-button ng-click="showAddJob($event)">Add</md-button></md-menu-item>
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
				    <div id="button" ng-click = "getThem()">
				      <i class="material-icons md-18">undo</i>
				    </div>
				    <div id="button">
				      <i class="material-icons md-18">redo</i>
				    </div>
					<div id="button" ng-click="lockTimeline()">
				      <i class="material-icons md-18">{{timelineLocked ? 'lock_outline' : 'lock_open'}}</i>
				    </div>
				    <div id="button" ng-click = "focusTimeline()">
				    	<md-tooltip md-direction="bottom" md-delay="1000">
          				Fit content
        				</md-tooltip>
				      <i class="material-icons md-18-90deg">vertical_align_center</i>
				    </div> 
				    <div id="button" ng-click="showAddJob($event)">
				      <i class="material-icons md-18">add</i>
				    </div>
			   	</div>
		   	</md-toolbar>
			<div id="page-container" ng-class="{'small':isSmall, 'big':!isSmall}">
			   	<div id="workspace" class="tabsdemoStaticTabs">
			       	<md-tabs class="md-accent main-tabs" md-align-tabs="bottom" md-stretch-tabs='always'>
			        	<md-tab id="tab1">
				     		<md-tab-label>Timeline</md-tab-label>
				     		<md-tab-body>
				       			<div id="my-timeline"></div>
				    		</md-tab-body>
			   			</md-tab>
			     		<md-tab id="tab2">
			     			<md-tab-label>Jobs</md-tab-label>
			     			<md-tab-body>
								<div class="radioDemo2 radioButtondemoMultiColumn">
								 	<div layout='row' layout-padding layout-align="start center" >
										<h4 class="no-margin" flex="30">Name</h4>
										<h4 class="no-margin" flex="45">Description</h4>
										<h4 class="no-margin" flex="10">Sequence</h4>
								    </div>
								    <md-divider></md-divider>
								    <md-radio-group ng-model="selectedIndex" >
									<div ng-repeat='job in jobs' class="row" layout-align="center start">
										<div layout="column"> 
											<div layout='row' layout-padding layout-align="start center" >
												<md-radio-button flex="30" ng-value="job.jobId" class="md-primary" style="margin:0px" >
											       {{job.name}}
												</md-radio-button>
												<div flex="45">
											    	{{job.description}}
											  	</div>
											  	<div flex="10">
											    	{{job.order}}
											  	</div>
												<div flex="15" ng-show="job.jobId==selectedIndex">
												    <md-button class="md-fab md-primary md-mini">
														<i class="material-icons md-24">mode_edit</i>
													</md-button>
													<md-button class="md-fab md-primary md-mini">
														<i class="material-icons md-24">delete</i>
													</md-button>
													<md-button class="md-fab md-primary md-mini" ng-click="job.showMore=true" ng-hide="job.showMore">
														<i class="material-icons md-24">keyboard_arrow_down</i>
													</md-button>
													<md-button class="md-fab md-primary md-mini" ng-click="job.showMore=false" ng-show="job.showMore">
														<i class="material-icons md-24">keyboard_arrow_up</i>
													</md-button>
											  	</div>
											</div>
											<div class="sub-list">
												<div layout='row' 
													layout-padding 
													layout-align="start center" 
													ng-repeat='task in job.tasks'
													ng-show="job.showMore" >
													<div flex="50">
												    	{{task.resource.name}}
												  	</div>
												  	<div flex="50">
												    	{{task.resource.secondsDuration}}
												  	</div>
												</div>
											</div>
										</div>
									</div>
								    </md-radio-group>
								    <md-divider></md-divider>
								</div>
			      			</md-tab-body>
			    		</md-tab>
			     		<md-tab id="tab2">
			      			<md-tab-label>Resources</md-tab-label>
			     			<md-tab-body>
								<div class="radioDemo2 radioButtondemoMultiColumn">
								 	<div layout='row' layout-padding layout-align="start center" >
										<h4 class="no-margin" flex="30">Name</h4>
										<h4 class="no-margin" flex="60">Description</h4>
								    </div>
								    <md-divider></md-divider>
								    <md-radio-group ng-model="selectedIndex2" >
										<div ng-repeat='res in resources' class="row" layout-align="center start">
											<div layout="column"> 
												<div layout='row' layout-padding layout-align="start center" >
													<md-radio-button flex="30" ng-value="res.resourceId" class="md-primary" style="margin:0px" >
												       {{res.name}}
													</md-radio-button>
													<div flex="60">
												    	{{res.description}}
												  	</div>
													<div flex="10" ng-show="res.resourceId==selectedIndex2">
													    <md-button class="md-fab md-primary md-mini">
															<i class="material-icons md-24">mode_edit</i>
														</md-button>
														<md-button class="md-fab md-primary md-mini">
															<i class="material-icons md-24">delete</i>
														</md-button>
												  	</div>
												</div>
											</div>
										</div>
								    </md-radio-group>
								    <md-divider></md-divider>
								</div>
			      			</md-tab-body>
			    		</md-tab>
					</md-tabs>
				</div>
			</div>
<			<md-toolbar class="my-bottom-toolbar">
	    		<div ng-hide="authenticated">
	    			<h5 class="no-margin">You are not logged in</h5>
	    		</div>
	    		<div ng-show="authenticated">
	    			<h5 class="no-margin">Welcome {{nam}} {{sur}} ({{log}})</h5>
	    		</div>
		   	</md-toolbar>
		</div>
	</body>
</html>
