package test.dastres.master;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.dastres.config.Constant;
import com.dastres.master.domain.ImportDataInfo;
import com.dastres.master.factory.Maker;
import com.dastres.master.thread.ImportDataThread;

public class ImportDataThreadTest extends TestCase {

	private Logger logger = Logger.getLogger(this.getClass());

	private final String zipTestFilePath = "dastres-master-test-import-data.zip";

	@Override
	protected void setUp() throws Exception {
		FileOutputStream out = new FileOutputStream(zipTestFilePath);
		out.write(makeTestdata());
		out.close();
		logger.info("make test file in :"
				+ new File(zipTestFilePath).getAbsolutePath());
	}

	@Override
	protected void tearDown() throws Exception {
		File f = new File(zipTestFilePath);
		logger.info("try to remove file:" + f.getAbsolutePath());
		assertTrue(f.delete());
	}

	private byte[] makeTestdata() throws Exception {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String separator = Constant.CSV_SEP_COMMA;
			ZipOutputStream zos = new ZipOutputStream(baos);
			for (int i = 0; i < 3; i++) {
				zos.putNextEntry(new ZipEntry(i + ".csv"));

				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < 5; j++) {
					sb.append(1 * i);
					sb.append(separator);
					sb.append(2 * i);
					sb.append(separator);
					sb.append("name" + i + j);
					sb.append(separator);
					sb.append("tag" + i + j);
					sb.append(separator);
					sb.append("tag" + (i + 1) + j + 1);
					sb.append(separator);

					sb.append(Constant.CSV_RECORD_SEPARATOR);
				}

				zos.write(sb.toString().getBytes(Constant.UTF8_ENCODING));
				zos.flush();
				zos.closeEntry();
			}
			zos.finish();
			zos.close();

			return baos.toByteArray();

		} catch (Throwable e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public final void testImportWithFile() {

		try {
			String separator = Constant.CSV_SEP_COMMA;
			ImportDataInfo importDataInfo = Maker.makeCSVZipDataInfo(
					new FileInputStream(zipTestFilePath), separator);
			new ImportDataThread(importDataInfo).run();
			assertTrue(true);
		} catch (Throwable e) {
			logger.error(e.getMessage(),e);
			fail(e.getMessage());
		}
	}

}
