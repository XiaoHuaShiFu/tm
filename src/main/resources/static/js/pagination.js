/**
 * 插入分页。
 * @param content:内容容器
 * @param sum:内容总数
 * @param maxPerPage:一页最多内容数
 * @param pageIndex:当前页索引
 * @author TAO
 */
function setPagination(content, sum, maxPerPage, pageIndex){
	if (sum > 0) {
		var footer = document.createElement("div");
		footer.setAttribute("class", "footer");
		content.appendChild(footer);
		var pagination = document.createElement("div");
		pagination.setAttribute("class", "my_pagination");
		footer.appendChild(pagination);
		var pageCount = parseInt(sum/maxPerPage);
		if (sum%maxPerPage != 0) pageCount++;
		var pgs = [];
		if (pageCount <= 10) {
			for (var i = 1; i <= pageCount; i++){
				pgs[i] = document.createElement("div");
				pgs[i].innerHTML = i;
				(function(i) {
					pgs[i].onclick = function() {
						window.location.href = i;
					}
				}(i));
				pagination.appendChild(pgs[i]);
			}
		}else {
			if (pageIndex < 7) {
				for (var i = 1; i <= 7; i++){
					pgs[i] = document.createElement("div");
					pgs[i].innerHTML = i;
					(function(i) {
						pgs[i].onclick = function() {
							window.location.href = i;
						}
					}(i));
					pagination.appendChild(pgs[i]);
				}
				var ect = document.createElement("span");
				ect.innerHTML="···";
				ect.style.marginRight = "9px";
				pagination.appendChild(ect);
				pgs[pageCount] = document.createElement("div");  //尾页索引块
				pgs[pageCount].innerHTML = pageCount;
				(function(pageCount) {
					pgs[pageCount].onclick = function() {
						window.location.href = pageCount;
					}
				}(pageCount));
				pagination.appendChild(pgs[pageCount]);
			}else {  //当前索引>=7时，显示首尾索引块，中间显示以当前索引块为对称线的7个索引块
				pgs[1] = document.createElement("div");  //首页页索引块
				pgs[1].innerHTML = 1;
				(function(first) {
					first.onclick = function() {
						window.location.href = 1;
					}
				}(pgs[1]));
				pagination.appendChild(pgs[1]);
				var ect = document.createElement("span");
				ect.innerHTML="···";
				ect.style.marginRight = "9px";
				pagination.appendChild(ect);
				var startPage = pageIndex-3;  //中间部分索引块的首索引
				var endPage = pageIndex+3;  //中间部分索引块的尾索引
				if (startPage > pageCount - 6) startPage = pageCount - 6;
				if (endPage > pageCount) endPage = pageCount;
				for (var i = startPage; i <= endPage; i++) {
					pgs[i] = document.createElement("div");
					pgs[i].innerHTML = i;
					(function(i) {
						pgs[i].onclick = function() {
							window.location.href = i;
						}
					}(i));
					pagination.appendChild(pgs[i]);
				}
				if (pageIndex + 3 < pageCount) {
					var ect2 = document.createElement("span");
					ect2.innerHTML="···";
					ect2.style.marginRight = "9px";
					pagination.appendChild(ect2);
					pgs[pageCount] = document.createElement("div");  //尾页索引块
					pgs[pageCount].innerHTML = pageCount;
					(function(pageCount) {
						pgs[pageCount].onclick = function() {
							window.location.href = pageCount;
						}
					}(pageCount));
					pagination.appendChild(pgs[pageCount]);
				}
			}
		}
		pgs[pageIndex].style.borderColor = "transparent";
		pgs[pageIndex].style.backgroundColor = "rgb(35,200,255)";
		pgs[pageIndex].style.color = "#fff";
	}
}