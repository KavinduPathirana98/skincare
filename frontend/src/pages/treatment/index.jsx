import { Fragment, useEffect, useState } from "react";
import {
  deleteTreatment,
  getAllTreatments,
  getAlltreatments,
  saveTreatment,
  searchTreatmentByID,
  updateTreatment,
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
  treatmentsRoute,
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

const Treatments = () => {
  const [treatments, setTreatments] = useState([]);
  const [modal, setModal] = useState(false);
  const [header, setHeader] = useState(AddKey);
  const [modalState, setModalState] = useState(AddKey);
  const [updateTreatmentID, setUpdateTreatmentID] = useState();
  const [form] = Form.useForm();

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Treatment Name",
      dataIndex: "treatmentName",
      key: "treatmentName",
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
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
  //Calling getall from service
  const getall = () => {
    getAllTreatments()
      .then((response) => setTreatments(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };

  //Calling update from service
  const update = async () => {
    try {
      const formValues = await form.validateFields();
      formValues.id = updateTreatmentID;
      updateTreatment(formValues)
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
    deleteTreatment(id)
      .then((response) => {
        console.log(response);
        if (response.data.responseCode == 1) {
          toast.success(succMsgUpdate);
          getall();
        } else {
          toast.error(errMsgDelete);
        }
      })
      .catch((err) => toast.error(errMsgGet));
  };
  //Calling search from service
  const searchfromID = () => {
    setTreatments([]); // Clear previous treatments

    searchTreatmentByID(keyword)
      .then((response) => {
        const treatmentsData = response.data.data;

        // Check if treatmentsData is an array and set it, or set it to an empty array if not
        if (Array.isArray(treatmentsData)) {
          setTreatments(treatmentsData);
        } else {
          let x = [treatmentsData];
          setTreatments(x); // Handle unexpected response format
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
      saveTreatment(formValues)
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
      setUpdateTreatmentID(record.id);
      form.setFieldValue("treatmentName", record.treatmentName);
      form.setFieldValue("price", record.price);
    }

    setModalState(key);
    if (key === UpdateKey) {
      setHeader(UpdateKey + " Treatment  ( ID:" + record.id + ")");
    } else {
      setHeader(insertString + " Treatment");
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
      update();
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
                placeholder="search Treatment by ID"
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
          <Table columns={columns} dataSource={treatments && treatments} />
        </Col>
        <Col md={3}></Col>
      </Row>
      <Modal isOpen={modal} toggle={() => toggle(AddKey)} size="md">
        <ModalHeader toggle={() => toggle(AddKey)}>{header}</ModalHeader>
        <ModalBody>
          <Form form={form}>
            <Row>
              <Col md={11}>
                <Label>First Name</Label>
                <Form.Item
                  name={"treatmentName"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="text" />
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Price</Label>
                <Form.Item
                  name={"price"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="number" />
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
export default Treatments;
