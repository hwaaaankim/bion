var nestableSorted = document.getElementById('nested-sortable');
var nestedSortables = [].slice.call(
	document.querySelectorAll(".nested-sortable")
);
nestedSortablesHandles =
	(nestedSortables &&
		Array.from(nestedSortables).forEach(function(e) {
			new Sortable(e, {
				group: "nested",
				animation: 150,
				fallbackOnBody: !0,
				swapThreshold: 0.65,
				invertSwap: true,
				onChange: function(e) {
					console.log(e.from.getAttribute('data-key'));
					console.log(e.to.getAttribute('data-key'));
					e.preventDefault();
					return false;
				},
				onAdd: function(e) {
					console.log('onAdd');
					e.preventDefault();
					return false;
				},
				onSort: function(e) {
					console.log('onSort');
					e.preventDefault();
					return false;
				},
				onEnd:function(evt){
					var itemEl = evt.item;  // dragged HTMLElement
					console.log(itemEl);
			        evt.to;    // target list
			        evt.from;  // previous list
			        evt.oldIndex;  // element's old index within old parent
			        evt.newIndex;  // element's new index within new parent
			        evt.oldDraggableIndex; // element's old index within old parent, only counting draggable elements
			        evt.newDraggableIndex; // element's new index within new parent, only counting draggable elements
			        evt.clone // the clone element
			        evt.pullMode = false;  // when item is in another sortable: `"clone"` if cloning, `true` if moving
					
					return false;
				},
				onMove:function(){
				}
			});
		}));

