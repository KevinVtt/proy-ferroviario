package com.mycompany.simuladorclientetren;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveException;

public class RarExtractor {

    public static void extractRar(String rarFilePath, String outputDirectory) throws IOException, ArchiveException {
        try (FileInputStream fis = new FileInputStream(rarFilePath);
             ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("rar", fis)) {

            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                if (!ais.canReadEntryData(entry)) {
                    continue;
                }

                File entryFile = new File(outputDirectory, entry.getName());
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    File parent = entryFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    try (FileOutputStream fos = new FileOutputStream(entryFile)) {
                        IOUtils.copy(ais, fos);
                    }
                }
            }
        }
    }}