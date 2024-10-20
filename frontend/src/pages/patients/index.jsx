import { Fragment, useEffect, useState } from "react";
import {
  deletePatient,
  getAllPatients,
  savePatient,
  searchPatientByID,
} from "../../service";
import { Button, Col, Form, Input, Row, Space, Table, Tag } from "antd";
import "antd/dist/antd";
import axios from "axios";
import {
  AddKey,
  api_url,
  errMsgDelete,
  errMsgGet,
  errMsgUpdate,
  insertString,
  patientsRoute,
  succMsgDelete,
  UpdateKey,
} from "../../constants";
import { toast } from "react-toastify";
import {
  Container,
  Label,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
} from "reactstrap";
import { render } from "@testing-library/react";
import { DeleteOutlined, EditOutlined, PlusOutlined } from "@ant-design/icons";
import { useForm } from "antd/es/form/Form";

const Patients = () => {
  const [patients, setPatients] = useState([]);
  const [modal, setModal] = useState(false);
  const [header, setHeader] = useState(AddKey);
  const [modalState, setModalState] = useState(AddKey);
  const [form] = Form.useForm();

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
          <tr>
            <td>
              <Button onClick={() => toggle(AddKey)}>
                <PlusOutlined />
              </Button>
            </td>
          </tr>
        );
      },

      render: (_, record) => {
        return (
          <tr>
            <td>
              <Button onClick={() => toggle(UpdateKey)}>
                <EditOutlined />
              </Button>
            </td>
            <td>
              <Button>
                <DeleteOutlined
                  onClick={() => {
                    deletefromID(record.id);
                  }}
                />
              </Button>
            </td>
          </tr>
        );
      },
      dataIndex: "actions",
    },
  ];
  //Calling getall from service
  const getall = () => {
    getAllPatients()
      .then((response) => setPatients(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };

  //Calling update from service
  const update = () => {};
  //Calling delete from service
  const deletefromID = (id) => {
    deletePatient(id)
      .then((response) => {
        if (response.data.responseCode == 1) {
          toast.success(succMsgDelete);
          getall();
        } else {
          toast.error(errMsgDelete);
        }
      })
      .catch((err) => toast.error(errMsgGet));
  };
  //Calling search from service
  const searchfromID = () => {
    setPatients([]); // Clear previous patients

    searchPatientByID(keyword)
      .then((response) => {
        console.log("Response:", response.data.data); // Check what response contains

        const patientsData = response.data.data;

        // Check if patientsData is an array and set it, or set it to an empty array if not
        if (Array.isArray(patientsData)) {
          setPatients(patientsData);
        } else {
          let x = [patientsData];
          setPatients(x); // Handle unexpected response format
          // toast.error("Invalid data format received");
        }
      })
      .catch((err) => {
        console.error(err); // Log the error to debug
        toast.error(errMsgGet); // Display an error notification
      });
  };
  //Calling insert from service
  const insert = async () => {
    try {
      const formValues = await form.validateFields();
      savePatient(formValues)
        .then((response) => {
          if (response.data.responseCode == 1) {
            getall();
          } else {
          }
        })
        .catch((err) => toast.error(errMsgUpdate));
    } catch (err) {}
  };
  const toggle = (key) => {
    console.log(key);
    setModalState(key);
    if (key === UpdateKey) {
      setHeader(UpdateKey + " Patient");
    } else {
      setHeader(insertString + " Patient");
    }
    setModal(!modal);
  };

  useEffect(() => {
    getall();
  }, []);
  const Submit = () => {
    if (modalState == AddKey) {
      insert();
    } else {
    }
  };
  const [keyword, setKeyword] = useState("");
  return (
    <Fragment>
      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Row>
            <Col md={20}>
              <Input
                value={keyword}
                onChange={(e) => {
                  setKeyword(e.target.value);
                }}
                placeholder="search patient by ID"
              />
            </Col>
            <Col md={4}>
              <Button
                onClick={() => {
                  searchfromID();
                }}
              >
                Search
              </Button>
            </Col>
          </Row>
        </Col>
        <Col md={3}></Col>
      </Row>

      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Table columns={columns} dataSource={patients && patients} />
        </Col>
        <Col md={3}></Col>
      </Row>
      <Modal isOpen={modal} toggle={() => toggle(AddKey)} size="md">
        <ModalHeader toggle={() => toggle(AddKey)}>
          {header} Patient
        </ModalHeader>
        <ModalBody>
          <Form form={form}>
            <Row>
              <Col md={11}>
                <Label>First Name</Label>
                <Form.Item name={"fName"}>
                  <Input type="text" />
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Last Name</Label>
                <Form.Item name={"lName"}>
                  <Input type="text" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col md={11}>
                <Label>NIC</Label>
                <Form.Item name={"nic"}>
                  <Input type="text" />
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Phone</Label>
                <Form.Item name={"phone"}>
                  <Input type="text" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col md={24}>
                <Label>Email</Label>
                <Form.Item name={"email"}>
                  <Input type="text" />
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button
            onClick={() => {
              Submit();
            }}
            color="default"
            variant="solid"
          >
            {"Submit"}
          </Button>
        </ModalFooter>
      </Modal>
    </Fragment>
  );
};
export default Patients;
