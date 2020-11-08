package com.opstty;

import com.opstty.job.Districts;
import com.opstty.job.Highest;
import com.opstty.job.MostTrees;
import com.opstty.job.NbTrees;
import com.opstty.job.Oldest;
import com.opstty.job.Sort;
import com.opstty.job.Species;
import com.opstty.job.WordCount;
import org.apache.hadoop.util.ProgramDriver;

public class AppDriver {
        public static void main(String argv[]) {
                int exitCode = -1;
                ProgramDriver programDriver = new ProgramDriver();

                try {
                        programDriver.addClass("wordcount", WordCount.class,
                                        "A map/reduce program that counts the words in the input files.");
                        programDriver.addClass("districts", Districts.class,
                                        "A map/reduce program that counts the number of districts in the input files.");
                        programDriver.addClass("species", Species.class,
                                        "A map/reduce program that counts the number of species in the input files.");
                        programDriver.addClass("nbTrees", NbTrees.class,
                                        "A map/reduce program that counts the number of trees by species in the input files.");
                        programDriver.addClass("highest", Highest.class,
                                        "A map/reduce program that finds the highest tree by species in the input files.");
                        programDriver.addClass("sort", Sort.class,
                                        "A map/reduce program that display the trees from smallest to highest in the input files.");
                        programDriver.addClass("oldest", Oldest.class,
                                        "A map/reduce program that display the district containing the oldest tree");
                        programDriver.addClass("most", MostTrees.class,
                                        "A map/reduce program that display the district containing the oldest tree");

                        exitCode = programDriver.run(argv);
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }

                System.exit(exitCode);
        }
}
