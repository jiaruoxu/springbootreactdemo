import {notification} from 'antd';


  const openNotification = (type, message, description) => {
    notification[type]({
      message: message,
      description:
      description,
    });
  };
 
  export const successNoticiation =  (message, description) => openNotification('success',message, description);
  export const infoNoticiation =  (message, description) => openNotification('info',message, description);
  export const warningNoticiation =  (message, description) => openNotification('warning',message, description);
  export const errorNoticiation =  (message, description) => openNotification('error',message, description);