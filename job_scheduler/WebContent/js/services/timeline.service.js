angular.module('app').service('timelineService', function () {
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
    vm.focus = focus;

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
        onMove: function (item, callback) {
            bufferService.push(items.get(item.id));
            callback(item);
            dataService.updateItem(item);
        }
    };

    function init(elementId) {
        setTimeout(function () {
            container = document.getElementById(elementId);
            timeline = new vis.Timeline(container, items, groups, options);
        }, 10);
    }

    function lock() {
        timeline.setOptions({
            editable: {
                add: false,
                updateTime: false,
                updateGroup: false,
                remove: false
            }
        });
    }

    function unlock() {
        timeline.setOptions({
            editable: {
                add: true,
                updateTime: true,
                updateGroup: false,
                remove: true
            }
        });
    }

    function showCurrentTime() {
        timeline.setOptions({showCurrentTime: true});
    }

    function hideCurrentTime() {
        timeline.setOptions({showCurrentTime: false});
    }

    function disableSnapping() {
        timeline.setOptions({snap: null})
    }

    function enableSnapping() {
        timeline.setOptions({snap: vis.timeline.TimeStep.snap})
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
});

