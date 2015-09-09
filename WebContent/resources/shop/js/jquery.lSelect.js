/*
 * Copyright 2013-2015 dp2345.com. All rights reserved.
 * Support: http://www.dp2345.com
 * License: http://www.dp2345.com/license
 * 为jQuery类添加“成员函数-lSelect”。jQuery类的实例可以使用这个“成员函数:lSelect”
 * ex: $("#id").lSelect({json字符串});
 * JavaScript - lSelect
 * Version: 3.0
 */

(function($) {
	$.fn.extend({
		lSelect: function(options) {
			var settings = {
				choose: "请选择...",
				emptyValue: "",
				cssStyle: {"margin-right": "4px"},
				url: null,
				type: "GET"
			};
			// 合并settings跟 options 也叫深度拷贝
			$.extend(settings, options);
			
			var cache = {};
			return this.each(function() {
				var $input = $(this);
				var id = $input.val();
				var treePath = $input.attr("treePath");
				var selectName = $input.attr("name") + "_select";
				
				if (treePath != null && treePath != "") {
					var ids = (treePath + id + ",").split(",");
					var $position = $input;
					for (var i = 1; i < ids.length; i ++) {
						$position = addSelect($position, ids[i - 1], ids[i]);
					}
				} else {
					addSelect($input, null, null);
				}
				
				function addSelect($position, parentId, currentId) {
					$position.nextAll("select[name=" + selectName + "]").remove();
					if ($position.is("select") && (parentId == null || parentId == "")) {
						return false;
					}
					if (cache[parentId] == null) {
						$.ajax({
							url: settings.url,
							type: settings.type,
							data: parentId != null ? {parentId: parentId} : null,
							dataType: "json",
							cache: false,
							async: false,
							success: function(data) {
								
								cache[parentId] = data;
							}
						});
					}
					var data = cache[parentId];
					if ($.isEmptyObject(data)) {
						return false;
					}
					var select = '<select name="' + selectName + '">';
					if (settings.emptyValue != null && settings.choose != null) {
						select += '<option value="' + settings.emptyValue + '">' + settings.choose + '</option>';
					}
					$.each(data, function(value, name) {
						if(value == currentId) {
							select += '<option value="' + value + '" selected="selected">' + name + '</option>';
						} else {
							select += '<option value="' + value + '">' + name + '</option>';
						}
					});
					select += '</select>';
					return $(select).css(settings.cssStyle).insertAfter($position).bind("change", function() {
						var $this = $(this);
						
						if ($this.val() == "") {
							var $prev = $this.prev("select[name=" + selectName + "]");
							if ($prev.size() > 0) {
								$input.val($prev.val());
							} else {
								$input.val(settings.emptyValue);
							}
						} else {
							$input.val($this.val());
						}
						addSelect($this, $this.val(), null);
					});
				}
			});
			
		}
	});
})(jQuery);