

// tbody 내용 생성
function renderTbody(data){
  const totalcnt = document.getElementById("totalcnt");
  totalcnt.innerText=`총 개수 : ${data.length}`;

  const tbody = document.querySelector("tbody");
  tbody.innerHTML = "";

  data.forEach(item => {
    const tr = document.createElement("tr");
    const tdChk = document.createElement("td");
    const chk = document.createElement("input");
    chk.type = "checkbox";
    tdChk.appendChild(chk);
    tr.appendChild(tdChk);

    const tdName = document.createElement("td");
    tdName.textContent = item.key;
    tr.appendChild(tdName);

    const tdSize =document.createElement("td");
    tdSize.textContent = item.size;
    tr.appendChild(tdSize);

    tbody.appendChild(tr);
  });
}