// S3 bucket 파일을 조회하고 내용으로 테이블 생성

const viewFilebtn=document.getElementById("viewFile");

viewFilebtn.addEventListener("click",function(){
  console.log("조회 버튼 클릭됨")
  fetch("/file/list")
  .then(response => {
    if(!response.ok){
      throw new Error(`HTTP error status:${response.status}`);
    }
    return response.json();
  })
  .then(data => {
    console.log(data);
    return data;
  })
  .then(data => {
    console.log(data);
    renderTbody(data);
  })
  .catch(err => {
    console.error("fetch 에러 : ", err);
  })
});

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
    chk.name = "originalFileName";
    chk.value = item.key;
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