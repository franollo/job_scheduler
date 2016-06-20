angular
    .module('app')
    .service('bufferService', bufferService);

bufferService.$inject = ['dataService'];

function bufferService(dataService) {
    var vm = this;
    var undo_buffer = [];
    var redo_buffer = [];
    vm.pushUndo = pushUndo;
    vm.undo = undo;
    vm.redo = redo;

    function pushUndo(item) {
        undo_buffer.push(items.get(item.id));
        undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
    }

    function undo(items) {
        var item = undo_buffer.pop();
        if (item != undefined) {
            redo_buffer.push(items.get(item.id));
            redo_buffer = redo_buffer.slice(redo_buffer.length - 10, redo_buffer.length);
            item.start = new Date(item.start);
            item.end = new Date(item.end);
            items.update(item);
            dataService.updateItem(item);
        }
    }

    function redo(items) {
        var item = redo_buffer.pop();
        if (item != undefined) {
            undo_buffer.push(items.get(item.id));
            undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
            item.start = new Date(item.start);
            item.end = new Date(item.end);
            items.update(item);
            dataService.updateItem(item);
        }
    }
}