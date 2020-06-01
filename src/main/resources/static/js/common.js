/**
 * 携带token跳转
 * @param trigger 触发跳转事件的元素，trigger需要设置href属性
 * @author TAO
 * @date 2020/6/1
 */
function jumpWithToken(trigger){
	var destination = trigger.getAttribute("href")
	if (location.href != location.href.substring(0, location.href.lastIndexOf("/") + 1) + destination){
		var form = document.createElement("form");
		form.setAttribute("action", destination);
		form.setAttribute("method", "post");
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "_header");
		input.setAttribute("value", "authorization:" + sessionStorage.getItem("token"));
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
		document.body.removeChild(form);
	}
}