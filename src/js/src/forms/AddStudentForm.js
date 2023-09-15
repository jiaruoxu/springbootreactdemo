import React from 'react';

import { Formik} from 'formik';
import { Button, Input, Tag } from 'antd';
import {AddNewStudent} from '../client'
 const InputBottomMargin = {marginBottom:'10px'};
 const tagStyle = {backgroundColor:'#f50' , color:"white",...InputBottomMargin};
 const AddStudentForm = (props) => (
     <Formik
       initialValues={{ firstName:'',lastName:'',email: '', gender: '' }}
       validate={values => {
         const errors = {};
         if(!values.firstName){
            errors.firstName = 'Required';
         }
         if(!values.lastName){
            errors.lastName = 'Required';
         }
       
         if(!values.gender){
            errors.gender = 'Required';
         }else if(! ['MALE','male','FEMALE','female'].includes(values.gender)){
            errors.gender = 'Gender must be (M,F)';
         }
         if (!values.email) {
           errors.email = 'Required';
         } else if (
           !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
         ) {
           errors.email = 'Invalid email address';
         }
         
         return errors;
       }}
       onSubmit={(student, { setSubmitting }) => {
        //  setTimeout(() => {
        //    alert(JSON.stringify(values, null, 2));
        //    setSubmitting(false);
        //  }, 400);
        AddNewStudent(student).then(()=>{
            // alert(JSON.stringify(student, null, 2));
            props.onSuccess();
            // setSubmitting(false);
        }).catch(err =>{
            console.log(err.message)
            props.OnFailure(err);
        }).finally(()=>{
            setSubmitting(false);
        })
       }}
     >
       {({
         values,
         errors,
         touched,
         handleChange,
         handleBlur,
         handleSubmit,
         isSubmitting,
         submitForm,
         isValid
         /* and other goodies */
       }) => (
         <form onSubmit={handleSubmit}>
           <Input
            style={InputBottomMargin}
             name="firstName"
             onChange={handleChange}
             onBlur={handleBlur}
             value={values.firstName}
             placeholder='First name. E.g. John'
           />
           {errors.firstName && touched.firstName
            && <Tag style={tagStyle}>{errors.firstName}</Tag>}
           <Input
        style={InputBottomMargin}
        name="lastName"
        onChange={handleChange}
        onBlur={handleBlur}
        value={values.lastName}
        placeholder='Last name. E.g. John'
      />
      {errors.lastName && touched.lastName && <Tag style={tagStyle}>{errors.lastName}</Tag>}
      <Input
        style={InputBottomMargin}
        name="email"
        onChange={handleChange}
        onBlur={handleBlur}
        value={values.email}
        placeholder='Email. E.g. example@gmail.com'
      />
      {errors.email && touched.email && <Tag style={tagStyle}>{errors.email}</Tag>}
      <Input
        style={InputBottomMargin}
        name="gender"
        onChange={handleChange}
        onBlur={handleBlur}
        value={values.gender}
        placeholder='Gender. E.g. Male or Female'
      />
      {errors.gender && touched.gender && <Tag style={tagStyle}>{errors.gender}</Tag>}
      
           <Button 
           onClick = {submitForm} type="submit" disabled={isSubmitting | (touched && !isValid)}>
             Submit
           </Button>
         </form>
       )}
     </Formik>

 );
 
 export default AddStudentForm;