package com.saas.notificationservice.grpc;

import com.saas.notificationservice.dto.NotificationTemplateResponse;
import com.saas.notificationservice.proto.*;
import com.saas.notificationservice.service.NotificationTemplateService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class NotificationTemplateGrpcService extends NotificationTemplateServiceGrpc.NotificationTemplateServiceImplBase {

    private final NotificationTemplateService service;
    private final ProtoConverter converter;

    @Override
    public void createTemplate(CreateTemplateRequest request, StreamObserver<TemplateResponse> responseObserver) {
        try {
            NotificationTemplateResponse dto = service.create(converter.toTemplateRequest(request));
            responseObserver.onNext(converter.toTemplateProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getTemplate(GetTemplateRequest request, StreamObserver<TemplateResponse> responseObserver) {
        try {
            NotificationTemplateResponse dto = service.getById(request.getId());
            responseObserver.onNext(converter.toTemplateProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAllTemplates(GetAllTemplatesRequest request, StreamObserver<TemplateListResponse> responseObserver) {
        try {
            List<NotificationTemplateResponse> dtos = service.getAll();
            TemplateListResponse.Builder builder = TemplateListResponse.newBuilder();
            dtos.forEach(dto -> builder.addTemplates(converter.toTemplateProto(dto)));
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void updateTemplate(UpdateTemplateRequest request, StreamObserver<TemplateResponse> responseObserver) {
        try {
            NotificationTemplateResponse dto = service.update(request.getId(), converter.toTemplateRequest(request));
            responseObserver.onNext(converter.toTemplateProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deleteTemplate(DeleteTemplateRequest request, StreamObserver<DeleteTemplateResponse> responseObserver) {
        try {
            service.delete(request.getId());
            responseObserver.onNext(DeleteTemplateResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Template deleted successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}

