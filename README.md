# MemryX Yocto Recipes

This repo contains two recipes for integrating the MemryX SDK into a Yocto image.


## meta-mx3-driver Usage

This recipe builds the kernel module for the MX3 M.2 card.

1. Add the path to this folder (`meta-mx3-driver`) to your build's `conf/bblayers.conf`
2. Include the `memx-cascade-plus-pcie` target in your Yocto build process

**NOTE**: your system may need device tree edits, especially if this is an ARM or RISCV system. These edits are out of scope of this overlay so please contact MemryX for support if the MX3's BAR / MSIX are not working with your default device tree.

## meta-memx-runtime Usage

This recipe builds the [MxAccl C++ Runtime Library](https://developer.memryx.com/api/accelerator/cpp.html) plus a binary [libmemx](https://developer.memryx.com/api/driver/driver.html).

To build it, the same steps apply:

1. Add the path to this folder (`meta-memx-runtime`) to your build's `conf/bblayers.conf`
2. Include the `memx-runtime` target in your Yocto build process
