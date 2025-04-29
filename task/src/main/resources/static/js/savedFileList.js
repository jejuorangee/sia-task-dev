const viewFilebtn=document.getElementById("viewFile");

viewFilebtn.addEventListener("click",function(){
  console.log("조회 버튼 클릭됨")
  fetch("/file/processed/list")
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

    const convertedFileName = document.createElement("td");
    convertedFileName.textContent = item.convertedFileName
    tr.appendChild(convertedFileName);

    const originalFileName = document.createElement("td");
    originalFileName.textContent = item.originalFileName
    tr.appendChild(originalFileName);

    const width = document.createElement("td");
    width.textContent = item.width
    tr.appendChild(width);

    const height = document.createElement("td");
    height.textContent = item.height
    tr.appendChild(height);


    const band_count = document.createElement("td");
    band_count.textContent = item.band_count
    tr.appendChild(band_count);

    tbody.appendChild(tr);
  });
}