package com.witkups.carloading.solution;

import com.witkups.carloading.TestFilesData;
import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.reader.FileReader;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

public class SolutionSerializerTest {
	@Test
	public void checkNoChangedInputSerializedResult() throws Exception {
		final InputStream instanceInpStream = new FileReader(TestFilesData.SAMPLE_INSTANCE_PATH).read();
		final InputStream solutionInpStream = new FileReader(TestFilesData.SAMPLE_SOLUTION_PATH).read();
		final Instance instance = new InstanceParser(instanceInpStream).parse();
		final Solution solution = new SolutionParser(solutionInpStream, instance).parse();
		final List<Section> sections = new SolutionSerializer(solution).serialize();
		final String sectionsInString = Section.stringifySections(sections);
		try (Stream<String> solutionOriginContent = Files.lines(Paths.get(TestFilesData.SAMPLE_SOLUTION_PATH))) {
			solutionOriginContent
					.reduce((a, b) -> a + System.lineSeparator() + b)
					.map(con -> con + System.lineSeparator()) // nie czyta pustej lini - wymaganie
					.ifPresent(originalContent -> assertEquals(originalContent, sectionsInString));
		}
	}
}