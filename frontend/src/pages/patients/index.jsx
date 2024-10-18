import { Fragment, useEffect, useState } from "react";
import { deletePatient, getAllPatients } from "../../service";
import { Button, Col, Row, Space, Table, Tag } from "antd";
import axios from "axios";
import { api_url, errMsgDelete, errMsgGet, patientsRoute, succMsgDelete } from "../../constants";
import { toast } from "react-toastify";
import { Container } from "reactstrap";
import { render } from "@testing-library/react";
import { DeleteOutlined, EditOutlined, PlusOutlined } from "@ant-design/icons";

const Patients = () => {
  const [patients, setPatients] = useState();

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "First Name",
      dataIndex: "fName",
      key: "fName",
    },
    {
      title: "Last Name",
      dataIndex: "lName",
      key: "lName",
    },
    {
      title: "NIC",
      dataIndex: "nic",
      key: "nic",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Phone",
      dataIndex: "phone",
      key: "phone",
    },
    {
      align: "center",
      title: () => {
        return (
          <tr >  
            <td>
              <Button>
                <PlusOutlined />
              </Button>
            </td>
      
          </tr>
        );
      },

      render: (_,record) => {
        return (
          <tr>
            <td>
              <Button>
                <EditOutlined />
              </Button>
            </td>
            <td>
              <Button>
                <DeleteOutlined onClick={()=>{deletefromID(record.id)}} />
              </Button>
            </td>
          </tr>
        );
      },
      dataIndex: "actions",
    },
  ];
  //Calling getall from service
  const getall=()=>{
    getAllPatients()
    .then((response) => setPatients(response.data.data))
    .catch((err) => toast.error(errMsgGet));
  }

//Calling update from service
const update=()=>{

}
//Calling delete from service
const deletefromID=(id)=>{
    deletePatient(id)
    .then((response) => response.data.responseCode==1?toast.success(succMsgDelete):toast.error(errMsgDelete), getall())
    .catch((err) => toast.error(errMsgGet));
   
}
//Calling search from service
const searchfromID=()=>{

}
//Calling insert from service
const insertfromID=()=>{

}
  useEffect(() => {
  getall()
  }, []);

  return (
    <Fragment>
      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Table columns={columns} dataSource={patients} />
        </Col>
        <Col md={3}></Col>
      </Row>
    </Fragment>
  );
};
export default Patients;
