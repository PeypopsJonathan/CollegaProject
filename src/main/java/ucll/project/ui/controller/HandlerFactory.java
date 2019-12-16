package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;

public class HandlerFactory {

    public HandlerFactory(){

    }

    public RequestHandler getHandler(String command, UserService userService) {
        RequestHandler handler = null;
        try {
            Class handlerClass = Class.forName("ucll.project.ui.controller." + command);
            Object handlerObject = handlerClass.getConstructor(String.class, UserService.class).newInstance(command, userService);
            handler = (RequestHandler) handlerObject;
        } catch (ClassNotFoundException e){
            throw new ControllerException(e.getMessage());
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
        return handler;
    }
}
