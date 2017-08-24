package com.witkups.carloading;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.reader.FileReader;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.SolutionSerializer;
import com.witkups.carloading.solver.Solver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SolutionConstructor {
	public static void main(String[] args) throws IOException {
//		int sectionsToRead = 3;
//		        List<Section> sections = new InputReader(sectionsToRead, System.in).read();
		//        sections.forEach(section -> section.getSectionStream().forEach(arr -> System.out.println(Arrays.toString(arr))));
//		final InputStream inputStream = new FileReader("src/test/resources/instance-file.txt").read();
		final Instance instance = new InstanceParser(System.in).parse();
		new Solver(instance).generateSolutions()
		                    .sequential()
		                    .map(SolutionConstructor::stringifySolution)
		                    .findFirst()
		                    .ifPresent(System.out::println);
	}

	public static String stringifySolution(Solution solution) {
		final List<Section> serialize = new SolutionSerializer(solution).serialize();
		return Section.stringifySections(serialize);
	}
}
