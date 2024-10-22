import { Fragment, useEffect, useState } from "react";
import {
  deleteAppointment,
  getAllappointment,
  getAllAppointments,
  getAllPatients,
  saveAppointment,
  searchAppointmentByDate,
  searchAppointmentByID,
  updateAppointment,
} from "../../service";
import {
  Button,
  Checkbox,
  Col,
  Form,
  Input,
  Row,
  Select,
  Space,
  Table,
  Tag,
} from "antd";
import "antd/dist/antd";
import axios from "axios";
import {
  AddKey,
  api_url,
  errMsgDelete,
  errMsgGet,
  errMsgUpdate,
  insertString,
  appointmentRoute,
  succMsgDelete,
  succMsgUpdate,
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

const Appoinments = () => {
  const [appointment, setAppoinments] = useState([]);
  const [patients, setPatients] = useState([]);
  const [modal, setModal] = useState(false);
  const [header, setHeader] = useState(AddKey);
  const [modalState, setModalState] = useState(AddKey);
  const [updateAppointmentID, setUpdateAppointmentID] = useState();
  const [form] = Form.useForm();

  const columns = [
    {
      title: "Appointment ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Patient ID",
      render: (record) => `${record.patient.id} `,
      key: "patientId",
    },
    {
      title: "Patient Name",
      key: "patientName",
      render: (record) => `${record.patient.fName} ${record.patient.lName}`,
    },

    {
      title: "Dermatologist",
      dataIndex: "dermatologist",
      key: "dermatologist",
    },
    {
      title: "Appointment Date",
      dataIndex: "appointmentDate",
      key: "appointmentDate",
    },
    {
      title: "Registration Fee",
      dataIndex: "registrationFee",
      key: "registrationFee",
    },
    {
      title: "Paid",
      render: (record) => {
        return record.paid ? (
          <Checkbox disabled={true} checked={true} />
        ) : (
          <Checkbox disabled={true} checked={false} />
        );
      },
      key: "paid",
    },
    {
      align: "center",
      title: () => {
        return (
          <tr>
            <td>
              <Button
                style={{ marginLeft: "15px" }}
                onClick={() => toggle(AddKey)}
              >
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
              <Button onClick={() => toggle(UpdateKey, record)}>
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
  //Get all Patients to insert into appoinments
  const getAllPatient = () => {
    getAllPatients()
      .then((response) => setPatients(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };
  //Calling getall from service
  const getall = () => {
    getAllAppointments()
      .then((response) => setAppoinments(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };

  //Calling update from service
  const update = async () => {
    try {
      const formValues = await form.validateFields();
      formValues.id = updateAppointmentID;
      formValues.patient = {
        id: formValues.patient,
        fName: "",
        lName: "",
        NIC: "",
        email: "",
        phone: "",
      };
      updateAppointment(formValues)
        .then((response) => {
          if (response.data.responseCode == 1) {
            getall();
            toast.success(succMsgUpdate);
          } else {
          }
        })
        .catch((err) => toast.error(errMsgUpdate));
    } catch (err) {}
  };
  //Calling delete from service
  const deletefromID = (id) => {
    deleteAppointment(id)
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
    setAppoinments([]); // Clear previous appointment

    searchAppointmentByID(keyword)
      .then((response) => {
        const appointmentData = response.data.data;
        console.log(response.data.data);
        // Check if appointmentData is an array and set it, or set it to an empty array if not
        if (Array.isArray(appointmentData)) {
          setAppoinments(appointmentData);
        } else {
          let x = [appointmentData];
          setAppoinments(x); // Handle unexpected response format
        }
      })
      .catch((err) => {
        console.error(err); // Log the error to debug
        toast.error(errMsgGet); // Display an error notification
      });
  };
  //search using date
  const searchfromDate = () => {
    setAppoinments([]); // Clear previous appointment

    searchAppointmentByDate(keywordDate)
      .then((response) => {
        const appointmentData = response.data.data;
        console.log(response.data.data);
        // Check if appointmentData is an array and set it, or set it to an empty array if not
        if (Array.isArray(appointmentData)) {
          setAppoinments(appointmentData);
        } else {
          let x = [appointmentData];
          setAppoinments(x); // Handle unexpected response format
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
      const patient = patients.filter((item) => {
        return item.id === formValues.patient;
      });

      formValues.patient = patient[0];

      saveAppointment(formValues)
        .then((response) => {
          if (response.data.responseCode == 1) {
            getall();
            toast.success(succMsgUpdate);
          } else {
          }
        })
        .catch((err) => toast.error(errMsgUpdate));
    } catch (err) {}
  };
  const toggle = (key, record) => {
    if (record) {
      setUpdateAppointmentID(record.id);
      form.setFieldValue("dermatologist", record.dermatologist);
      form.setFieldValue("patient", record.patient.id);
      form.setFieldValue("appointmentDate", record.appointmentDate);
      form.setFieldValue("paid", record.paid);
    }

    setModalState(key);
    if (key === UpdateKey) {
      setHeader(UpdateKey + " Appointment  ( ID:" + record.id + ")");
    } else {
      setHeader(insertString + " Appointment");
    }
    setModal(!modal);
  };

  useEffect(() => {
    getall();
    getAllPatient();
  }, []);
  const Submit = () => {
    if (modalState == AddKey) {
      insert();
    } else {
      update();
    }
  };
  const [keyword, setKeyword] = useState("");
  const [keywordDate, setKeywordDate] = useState("");
  return (
    <Fragment>
      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={9}>
          <Row>
            <Col md={10}>
              <Input
                value={keyword}
                onChange={(e) => {
                  setKeyword(e.target.value);
                }}
                placeholder="search Appointment by ID"
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
        <Col md={9}>
          <Row>
            <Col md={10}>
              <Input
                value={keywordDate}
                type="datetime-local"
                onChange={(e) => {
                  setKeywordDate(e.target.value);
                }}
                placeholder="search Appointment by DateTime"
              />
            </Col>
            <Col md={4}>
              <Button
                onClick={() => {
                  searchfromDate();
                }}
              >
                Search
              </Button>
            </Col>
          </Row>
        </Col>
      </Row>

      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Table columns={columns} dataSource={appointment && appointment} />
        </Col>
        <Col md={3}></Col>
      </Row>
      <Modal isOpen={modal} toggle={() => toggle(AddKey)} size="md">
        <ModalHeader toggle={() => toggle(AddKey)}>{header}</ModalHeader>
        <ModalBody>
          <Form form={form} initialValues={{ patient: -1 }}>
            <Row>
              <Col md={24}>
                <Label>Patient</Label>
                <Form.Item
                  name={"patient"}
                  rules={[
                    { required: true, message: "Required" },
                    {
                      validator: (_, value) => {
                        if (value === -1) {
                          return Promise.reject();
                        } else {
                          return Promise.resolve();
                        }
                      },
                      message: "Required",
                    },
                  ]}
                >
                  <Select>
                    <Select.Option value={-1}>
                      ---Select Patient---
                    </Select.Option>
                    {patients &&
                      patients.map((patient) => {
                        return (
                          <Select.Option value={patient.id}>
                            {patient.fName + " " + patient.lName}
                          </Select.Option>
                        );
                      })}
                  </Select>
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col md={11}>
                <Label>Dermatologist</Label>
                <Form.Item
                  name={"dermatologist"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="text" />
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Appointment Time</Label>
                <Form.Item
                  name={"appointmentDate"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="datetime-local" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col>
                <Form.Item valuePropName="checked" name={"paid"}>
                  <Checkbox>Paid</Checkbox>
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
export default Appoinments;
