package com.saas.notificationservice.grpc;

import com.saas.notificationservice.proto.*;
import com.saas.notificationservice.service.NotificationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class NotificationGrpcService extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final NotificationService service;
    private final ProtoConverter converter;

    @Override
    public void createNotification(CreateNotificationRequest request, StreamObserver<com.saas.notificationservice.proto.NotificationResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.NotificationResponse dto = service.create(converter.toNotificationRequest(request));
            responseObserver.onNext(converter.toNotificationProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getNotification(GetNotificationRequest request, StreamObserver<com.saas.notificationservice.proto.NotificationResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.NotificationResponse dto = service.getById(request.getId());
            responseObserver.onNext(converter.toNotificationProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAllNotifications(GetAllNotificationsRequest request, StreamObserver<NotificationListResponse> responseObserver) {
        try {
            List<com.saas.notificationservice.dto.NotificationResponse> dtos = service.getAll();
            NotificationListResponse.Builder builder = NotificationListResponse.newBuilder();
            dtos.forEach(dto -> builder.addNotifications(converter.toNotificationProto(dto)));
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void updateNotification(UpdateNotificationRequest request, StreamObserver<com.saas.notificationservice.proto.NotificationResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.NotificationResponse dto = service.update(request.getId(), converter.toNotificationRequest(request));
            responseObserver.onNext(converter.toNotificationProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deleteNotification(DeleteNotificationRequest request, StreamObserver<DeleteNotificationResponse> responseObserver) {
        try {
            service.delete(request.getId());
            responseObserver.onNext(DeleteNotificationResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Notification deleted successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}

