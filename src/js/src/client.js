
const checkStatus = (response) =>{
    







    
    if(response.ok){
        return response;
    }else{
        console.log(response.error());
        let error = new Error(response.statusText);
        error.response = response;
        response.json().then(e=>{
            error.error = e;
        });
        return Promise.reject(error);
    }
}
export const getAllStudents = () => fetch("api/students").then(checkStatus);
export const AddNewStudent =  student => 
  fetch("api/students",
{
    headers:{
        'Content-Type':'application/json'
    },
    method:'POST',
    body: JSON.stringify(student)
// }).then(checkStatus);
}) .then(async (response) => {
    if (response.ok) {
        return response;
    }
    let error = new Error(response.statusText);
    var json = await response.json();
    error.response = response;
        error.error = json;
    return Promise.reject(error);

  })