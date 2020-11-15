package com.mori5.itsecurity.logging.service.impl;

import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.domain.DocumentLog;
import com.mori5.itsecurity.logging.repository.DocumentLogRepository;
import com.mori5.itsecurity.logging.service.DocumentLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentLoggingServiceImpl implements DocumentLoggingService {

    private final DocumentLogRepository documentLogRepository;

    public void logDeleting(User actor, Document entity) {
        documentLogRepository.save(
                DocumentLog.builder()
                        .actor(actor)
                        .documentOf(entity.getUploader())
                        .operation("DELETE")
                        .build()
        );
    }

    public void logSave(User actor, Document entity) {
        documentLogRepository.save(
                DocumentLog.builder()
                        .actor(actor)
                        .documentOf(entity.getUploader())
                        .operation("SAVE")
                        .build()
        );
    }

}
