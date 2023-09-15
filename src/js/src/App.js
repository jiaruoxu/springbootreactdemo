import React, { useEffect, useState } from "react";
import logo from './logo.svg';
import './App.css';
import { getAllStudents } from './client';
import { Container } from "./Container";
import Footer from "./Footer";
import { Avatar, Modal, Spin, Table, Empty } from "antd";
import AddStudentForm from './forms/AddStudentForm'
import { errorNoticiation } from "./Notification";

function App() {

  const [state, setState] = useState(
    {
      students: [],
      isFetching: false,
      isAddStudentModalVisible: false

    });
  useEffect(() => {
    fetchStudent();
    console.log('i fire once');
  }, []);

  const openAddStudentModal = () => setState({ ...state, isAddStudentModalVisible: true });
  const closeAddStudentModal = () => setState({ ...state, isAddStudentModalVisible: false });
  const fetchStudent = () => {
    setState({ ...state, isFetching: true });
    getAllStudents().then(response => response.json()
      .then(students => {
        setState({
          ...state,
          students: students,
          isFetching: false
        });

      })).catch(error => {
        const message = error.message;
        errorNoticiation(message, message)
        setState({
          ...state,
          isFetching: false
        });
      });
  }

  const { students, isFetching, isAddStudentModalVisible } = state;
  const commonElements = () => (
    <div>
      <Modal title='add new studetn'
        open={isAddStudentModalVisible}
        onOk={closeAddStudentModal}
        onCancel={closeAddStudentModal}
        width={1000}
      >

        <AddStudentForm
          onSuccess={() => {
            closeAddStudentModal();
            fetchStudent();
          }}
          OnFailure = {(err)=>{
            // console.log(JSON.stringify(err));
            errorNoticiation(err.message,err.error.message);
          }}
        />
      </Modal>
      <Footer
        numberOfStudents={students.length}
        handleAddStudentClickEvent={openAddStudentModal} />
    </div>
  );
  if (isFetching) {
    return (<Container><Spin /></Container>)
  }

  if (students && students.length) {

    const columns = [
      {
        title: "",
        key: 'avatar',
        render: (student) => (
          <Avatar size='large'>
            {`${student.lastName.charAt(0).toUpperCase()}`}
          </Avatar>
        )
      },
      {
        title: 'StudentId',
        dataIndex: 'studentId',
        key: 'studentId',

      },
      {
        title: 'FirstName',
        dataIndex: 'firstName',
        key: 'firstName',

      },
      {
        title: 'LastName',
        dataIndex: 'lastName',
        key: 'lastName',

      },
      {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',

      },
      {
        title: 'Gender',
        dataIndex: 'gender',
        key: 'gender',

      }
    ];

    return <Container>
      <Table
        style={{ marginBottom: '100px' }}
        dataSource={students}
        pagination={false}
        columns={columns}
        rowKey="studentId"

      />

      {commonElements()}

    </Container>
  }
  return (<Container>
    <Empty
      description={<h1>no student found</h1>
      } />
    {commonElements()}
  </Container>)
}

export default App;
