<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.shopRank.edit")} - Powered By CETVISION</title>
<meta name="author" content="CETVISION Team" />
<meta name="copyright" content="CETVISION" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $amount = $("#amount");
	var $isSpecial = $("#isSpecial");
	
	[@flash_message /]
	
	// 特殊会员等级修改
	$isSpecial.click(function() {
		if ($(this).prop("checked")) {
			$amount.val("").prop("disabled", true);
		} else {
			$amount.prop("disabled", false);
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: {
				required: true,
				remote: {
					url: "check_name.jhtml?previousName=${shopRank.name?url}",
					cache: false
				}
			},
			scale: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			amount: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				},
				remote: {
					url: "check_amount.jhtml?previousAmount=${shopRank.amount}",
					cache: false
				}
			}
		},
		messages: {
			name: {
				remote: "${message("admin.validate.exist")}"
			},
			amount: {
				remote: "${message("admin.validate.exist")}"
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.shopRank.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${shopRank.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ShopRank.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${shopRank.name}" maxlength="100" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ShopRank.scale")}:
				</th>
				<td>
					<input type="text" name="scale" class="text" value="${shopRank.scale}" maxlength="7" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ShopRank.amount")}:
				</th>
				<td>
					<input type="text" id="amount" name="amount" class="text" value="${shopRank.amount}" maxlength="16"[#if shopRank.isSpecial] disabled="disabled"[/#if] />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isDefault" value="true"[#if shopRank.isDefault] checked="checked" disabled="disabled"[/#if] />${message("ShopRank.isDefault")}
						<input type="hidden" name="_isDefault" value="false" />
					</label>
					<label title="${message("admin.shopRank.isSpecialTitle")}">
						<input type="checkbox" id="isSpecial" name="isSpecial" value="true"[#if shopRank.isSpecial] checked="checked"[/#if] />${message("ShopRank.isSpecial")}
						<input type="hidden" name="_isSpecial" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>