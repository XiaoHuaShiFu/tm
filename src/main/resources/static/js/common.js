/**
 * 携带token跳转
 * @param trigger 触发跳转事件的元素，trigger需要设置href属性
 * @author TAO
 * @date 2020/6/1
 */
function jumpWithToken(trigger) {
	var destination = trigger.getAttribute("href")
	if (location.href != location.href.substring(0, location.href.lastIndexOf("/") + 1) + destination) {
		var form = document.createElement("form");
		form.setAttribute("action", destination);
		form.style.display = "none";
		document.body.appendChild(form);
		submitWithToken(form);
		document.body.removeChild(form);
	}
}

/**
 * 携带token提交，提交到当前页面
 * @param form 提交的表单
 * @author TAO
 * @date 2020/6/2
 */
function submitWithToken(form) {
	form.setAttribute("method", "post");
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", "_header");
	input.setAttribute("value", "authorization:" + sessionStorage.getItem("token"));
	form.appendChild(input);
	form.submit();
}