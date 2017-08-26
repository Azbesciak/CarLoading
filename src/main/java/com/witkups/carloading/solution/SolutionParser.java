package com.witkups.carloading.solution;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.processing.Parser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.reader.InputReader;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.PackagePlacementsParser;
import com.witkups.carloading.solution.purpose.Purpose;
import com.witkups.carloading.solution.purpose.PurposeParser;

import java.io.InputStream;
import java.util.List;

import static com.witkups.carloading.solution.SolutionSectionStructure.*;

public class SolutionParser implements Parser<Solution> {
	private List<Section> sections;
	private final Instance instance;

	public SolutionParser(InputStream solutionInputStream, Instance instance) {
		this.instance = instance;
		this.sections = new InputReader(SOLUTION_SECTIONS, solutionInputStream).read();
	}

	@Override
	public Solution parse() {
		final Purpose purpose = getPurpose();
		final List<PackagePlacement> packagePlacements = getPlacementOfPackages();
		return new Solution(purpose, packagePlacements);
	}

	private Purpose getPurpose() {
		return new PurposeParser(getSection(PURPOSE_SECTION_INDEX)).parse();
	}

	private List<PackagePlacement> getPlacementOfPackages() {
		return new PackagePlacementsParser(getSection(PACKAGE_PLACEMENT_SECTION_INDEX), instance.getPackages()).parse();
	}

	private Section getSection(int index) {
		return sections.get(index);
	}
}