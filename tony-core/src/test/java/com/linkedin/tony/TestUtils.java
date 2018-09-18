/**
 * Copyright 2018 LinkedIn Corporation. All rights reserved. Licensed under the BSD-2 Clause license.
 * See LICENSE in the project root for license information.
 */
package com.linkedin.tony;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class TestUtils {
  @Test
  public void testParseMemoryString() {
    assertEquals(Utils.parseMemoryString("2g"), "2048");
    assertEquals(Utils.parseMemoryString("2M"), "2");
    assertEquals(Utils.parseMemoryString("3"), "3");
  }

  @Test
  public void testPoll() {
    assertTrue(Utils.poll(() -> true, 1, 1));
    assertFalse(Utils.poll(() -> false, 1, 1));
  }

  @Test
  public void testUnzipArchive() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("test.zip").getFile());
    try {
      Utils.unzipArchive(file.getPath(), "venv/");
      Path unzippedFilePath = Paths.get("venv/123.xml");
      assertTrue(Files.exists(unzippedFilePath));
      Files.deleteIfExists(Paths.get("venv/123.xml"));
      Files.deleteIfExists(Paths.get("venv/"));
    } catch (IOException e) {
      fail(e.toString());
    }
  }

  @Test
  public void testIsArchive() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file1 = new File(classLoader.getResource("test.zip").getFile());
    File file2 = new File(classLoader.getResource("test.tar").getFile());
    File file3 = new File(classLoader.getResource("test.tar.gz").getFile());
    assertTrue(Utils.isArchive(file1.getAbsolutePath()));
    assertTrue(Utils.isArchive(file2.getAbsolutePath()));
    assertTrue(Utils.isArchive(file3.getAbsolutePath()));
  }

  @Test
  public void testRenameFile() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file1 = new File(classLoader.getResource("file_move.txt").getFile());
    boolean result = Utils.renameFile(file1.getAbsolutePath(),
                                      file1.getAbsolutePath() + "bak");
    assertTrue(Files.exists(Paths.get(file1.getAbsolutePath() + "bak")));
    assertTrue(result);
    Files.deleteIfExists(Paths.get(file1.getAbsolutePath() + "bak"));
  }
}
