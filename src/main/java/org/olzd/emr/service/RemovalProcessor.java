package org.olzd.emr.service;

import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.ExaminationCard;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.TreeNodeType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class RemovalProcessor {
    private static final Map<TreeNodeType, RemovalProcessor> strategies = new HashMap<>(4);

    static {
        RemovalProcessor removeAnalysisAttachmentProcessor = new RemovalProcessor() {
            @Override
            public void removeNode(MedicalCard card, Object data) throws IOException {
                MedicalCardService medicalCardService = new MedicalCardService();
                Iterator<AttachedFileWrapper> iter = card.getAnalysisAttachedFiles().iterator();

                while (iter.hasNext()) {
                    AttachedFileWrapper next = iter.next();
                    if (next == data) {
                        medicalCardService.removeAnalysisFileRecord(card, next);
                        Files.delete(Paths.get(next.getPathToFile()));
                        return;
                    }
                }
            }
        };
        RemovalProcessor removeTechExaminationAttachmentProcessor = new RemovalProcessor() {
            @Override
            public void removeNode(MedicalCard card, Object data) throws IOException {
                MedicalCardService medicalCardService = new MedicalCardService();
                Iterator<AttachedFileWrapper> iter = card.getTechExaminationFiles().iterator();

                while (iter.hasNext()) {
                    AttachedFileWrapper next = iter.next();
                    if (next == data) {
                        medicalCardService.removeTechExaminationFileRecord(card, next);
                        Files.delete(Paths.get(next.getPathToFile()));
                        return;
                    }
                }
            }
        };
        RemovalProcessor removeExaminationSheetProcessor = new RemovalProcessor() {
            @Override
            public void removeNode(MedicalCard card, Object data) throws IOException {
                MedicalCardService medicalCardService = new MedicalCardService();
                Iterator<ExaminationCard> iter = card.getExamCards().iterator();
                while (iter.hasNext()) {
                    ExaminationCard next = iter.next();
                    if (next == data) {
                        medicalCardService.removeExamSheet(card, next);
                        return;
                    }
                }
            }
        };
        RemovalProcessor removeSurgeryAttachmentProcessor = new RemovalProcessor() {
            @Override
            public void removeNode(MedicalCard card, Object data) throws IOException {
                MedicalCardService medicalCardService = new MedicalCardService();
                Iterator<AttachedFileWrapper> iter = card.getSurgeriesFiles().iterator();
                while (iter.hasNext()) {
                    AttachedFileWrapper next = iter.next();
                    if (next == data) {
                        medicalCardService.removeSurgeryAttachmentRecord(card, next);
                        Files.delete(Paths.get(next.getPathToFile()));
                        return;
                    }
                }
            }
        };
        strategies.put(TreeNodeType.ANALYSIS_FILE, removeAnalysisAttachmentProcessor);
        strategies.put(TreeNodeType.TECH_EXAMINATION_FILE, removeTechExaminationAttachmentProcessor);
        strategies.put(TreeNodeType.EXAMINATION_SHEET, removeExaminationSheetProcessor);
        strategies.put(TreeNodeType.SURGERY_FILE, removeSurgeryAttachmentProcessor);
    }

    public static RemovalProcessor getProcessor(TreeNodeType nodeType) {
        return strategies.get(nodeType);
    }

    public void removeNode(MedicalCard card, Object data) throws IOException {

    }
}
