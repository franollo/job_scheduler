angular
    .module('app')
    .service('timelineService', timelineService);

function timelineService(dataService) {
    var vm = this;
    vm.init = init;
    vm.lock = lock;
    vm.unlock = unlock;
    vm.showCurrentTime = showCurrentTime;
    vm.hideCurrentTime = hideCurrentTime;
    vm.disableSnapping = disableSnapping;
    vm.enableSnapping = enableSnapping;
    vm.getItems = getItems;
    vm.getGroups = getGroups;
    vm.addItems = addItems;
    vm.addGroups = addGroups;
    vm.focus = focus;
    vm.clean = clean;
    vm.zoomableKeyTrue = zoomableKeyTrue;
    vm.zoomableKeyFalse = zoomableKeyFalse;


    var groups = new vis.DataSet();
    var items = new vis.DataSet();
    var container;
    var timeline;
    var options = {
        height: '100%',
        editable: {add: true, updateTime: true, updateGroup: false, remove: true},
        orientation: 'top',
        margin: 0,
        stack: false,
        multiselect: true,
        groupEditable: true,
        zoomKey: 'ctrlKey',
        onMove: function (item, callback) {
            delete item.group;
            delete item.content;
            callback(item);
            dataService.saveItem(item);
        }
    };

    function addItems(itemsToAdd) {
        items.add(itemsToAdd);
    }

    function addGroups(groupsToAdd) {
        groups.add(groupsToAdd);
    }

    function init(elementId) {
        setTimeout(function () {
            container = document.getElementById(elementId);
            timeline = new vis.Timeline(container, items, groups, options);
        }, 10);
    }

    function lock() {
        options.editable.add = false;
        options.editable.updateTime = false;
        options.editable.updateGroup = false;
        options.editable.remove = false;
        timeline.setOptions(options);
    }

    function unlock() {
        options.editable.add = true;
        options.editable.updateTime = true;
        options.editable.updateGroup = false;
        options.editable.remove = true;
        timeline.setOptions(options);
    }

    function showCurrentTime() {
        options.showCurrentTime = true;
        timeline.setOptions(options);
    }

    function zoomableKeyTrue() {
        options.zoomKey = 'ctrlKey';
        timeline.setOptions(options);
    }

    function zoomableKeyFalse() {
        options.zoomKey = '';
        timeline.setOptions(options);
    }

    function hideCurrentTime() {
        options.showCurrentTime = false;
        timeline.setOptions(options);
    }

    function disableSnapping() {
        options.snap = null;
        timeline.setOptions(options);
    }

    function enableSnapping() {
        options.snap = vis.timeline.TimeStep.snap;
        timeline.setOptions(options);
    }

    function focus() {
        timeline.fit({animation: true});
    }

    function getItems() {
        return items;
    }

    function getGroups() {
        return groups;
    }

    function clean() {
        groups.clear();
        items.clear();
    }
}

